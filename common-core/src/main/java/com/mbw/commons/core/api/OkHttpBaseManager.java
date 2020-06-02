package com.mbw.commons.core.api;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbw.commons.lang.exception.OkHttpException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * OkHttp 服务
 *
 * @author Mabowen
 * @date 2020-06-02 09:04
 */
@Slf4j
public abstract class OkHttpBaseManager {
    private final static int LIMIT_PER_SECONDS = 100;
    private final static String CHARSET = "UTF-8";

    @Autowired
    private OkHttpConfig okHttpConfig;

    private AtomicInteger speedPerSecond = new AtomicInteger(0);
    private long currentSecond = 0;

    private String accessToken = "";
    protected Integer expiryTime = 0;
    private Integer retryRefreshToken = 0;

    private static Validator validator;

    public abstract OkHttpBaseResponse<LoginResponseData> refreshAccessToken();

    protected Object createApiProxy(Class<?> cls) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                // 限流
                limiting();

                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type","application/json")
//                        .addHeader("Authorization", "Bearer " + accessToken)
//                        .addHeader("AppId", okHttpConfig.getAppId())
//                        .addHeader("AppSecret", okHttpConfig.getAppSecret())
                        .build();

                long beginTime = System.currentTimeMillis();

                Response response = chain.proceed(request);
                response.header("Content-Type","application/json");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Sink sink = Okio.sink(baos);
                BufferedSink bufferedSink = Okio.buffer(sink);

                if(request.body() != null) {
                    request.body().writeTo(bufferedSink);
                }

                bufferedSink.close();
                sink.close();

                System.out.println(String.format("RequestBody: %s", baos.toString(CHARSET)));

                assert response.body() != null;
                BufferedSource source = response.body().source();
                source.request(Long.MAX_VALUE);

                String body = source.getBuffer()
                        .clone()
                        .readString(Charset.forName(CHARSET));

                OkHttpApiData apiData = OkHttpApiData.builder()
                        .url(request.url().encodedPath())
                        .query(request.url().query())
                        .requestBody(baos.toString(CHARSET))
                        .responseBody(body)
                        .httpMethod(request.method())
                        .httpStatus(response.code())
                        .httpMessage(response.message())
                        .duration(System.currentTimeMillis() - beginTime)
                        .build();

                System.out.println(String.format("ResponseBody: %s", body));

                if (log.isDebugEnabled()) {
                    log.debug("ApiData: " + apiData.toString());
                }

                //同步方式记录事件，请求量大时不适用
                OkHttpEventBusFactory.getInstance()
                        .getDefaultEventBus()
                        .post(apiData);

                //异步方式记录事件
//                OkHttpEventBusFactory.getInstance()
//                        .getDefaultAsyncEventBus()
//                        .post(apiData);

                return response;
            }
        }).connectTimeout(okHttpConfig.getTimeout(), TimeUnit.MILLISECONDS).build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        /**
         * 转换工厂
         * JaxbConverterFactory xml数据
         * GsonConverterFactory json数据
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(okHttpConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(cls);
    }

    protected <T extends OkHttpBaseResponse<R>, R> T execute(Call<T> caller) {
        try {
            retrofit2.Response<T> response = caller.execute();
            if(response.isSuccessful()) {
                if(retryRefreshToken > 0) {
                    retryRefreshToken = 0;
                }
                return response.body();
            } else if(response.code() == HttpStatus.FORBIDDEN.value()) {
                // HttpStatus.FORBIDDEN.value() == 403
                if(retryRefreshToken < 3) {
                    retryRefreshToken++;
                    //Token失效，重新触发一次调用，refresh会清除retryRefreshToken，触发 retryRefreshToken = 0
                    refreshAccessToken();
                    response = caller.execute();
                } else {
                    throw new OkHttpException("身份接口调用异常, 可能是Token失效  http code = " + response.code());
                }
                return response.body();
            } else {
                throw new OkHttpException("接口调用异常,  http code = " + response.code());
            }
        } catch(IOException exp) {
            log.error(exp.getMessage(), exp);
            throw new OkHttpException("接口调用异常" + exp.getMessage(), exp);
        }
    }

    /**
     * 	检测token是否失效,如果失效更换Token
     */
    public void scheduleRefreshToken() {
        //提60S刷新Token
        log.info("Token过期时间还差： " + (int)(System.currentTimeMillis()/1000 - expiryTime));
        if(System.currentTimeMillis()/1000 - expiryTime > -60 * 60) {
            refreshAccessToken();
        }
    }

    /**
     * 参数校验
     * @param t
     * @return
     */
    protected <T> Result validate(T t) {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    /**
     * 限流 每秒100次
     */
    private void limiting() {
        if(currentSecond != System.currentTimeMillis() / 1000) {
            currentSecond = System.currentTimeMillis() / 1000;
            speedPerSecond.set(0);
        }

        speedPerSecond.addAndGet(1);
        if(speedPerSecond.intValue() > LIMIT_PER_SECONDS) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new OkHttpException(e.getMessage(), e);
            }
        }
    }

    public synchronized String getAccessToken() {
        return accessToken;
    }

    public synchronized void setAccessToken(String token) {
        this.accessToken = token;
    }
}
