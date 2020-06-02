package com.mbw.commons.core.api;

import org.apache.curator.shaded.com.google.common.eventbus.Subscribe;

import javax.annotation.PostConstruct;

/**
 * OkHttp 调用监控
 *
 * @author Mabowen
 * @date 2020-06-02 09:15
 */
public class OkHttpMonitorManager {
    @PostConstruct
    public void init() {
        OkHttpEventBusFactory busFactory = OkHttpEventBusFactory.getInstance();
        busFactory.registerDefaultAsyncEventBus(this);
        busFactory.registerDefaultEventBus(this);
    }

    @Subscribe
    public void onApiData(OkHttpApiData apiData) {
        String requestUrl = "?" + apiData.getQuery();
        //保存OkHttpApi请求记录
        System.out.println(apiData);
    }
}
