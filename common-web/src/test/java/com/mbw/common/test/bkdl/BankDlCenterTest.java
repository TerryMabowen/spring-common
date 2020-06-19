package com.mbw.common.test.bkdl;

import com.mbw.commons.util.http.HttpClientUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-17 18:50
 */
public class BankDlCenterTest {

    @Test
    public void updateAccountLastSyncTime() {

    }

    private List<ParamData> f1(Integer type) {
        List<ParamData> params = new ArrayList<>();

        //1.
        params.add(f2(type, ""));
        //2.
        params.add(f2(type, ""));

        return params;
    }

    private ParamData f2(Integer type, String accountNo) {
        return ParamData.builder()
                .accountNo(accountNo)
                .time("")
                .type(type)
                .build();
    }

    private void f3(List<ParamData> params) {
        if (!params.isEmpty()) {
            params.forEach(item -> {
                Map<String, String> map = new HashMap<>();
                map.put("accountNo", item.getAccountNo());
                map.put("time", item.getTime());
                map.put("type", item.getType().toString());

                String result = HttpClientUtil.doGet("url", map);
            });
        }


    }
}
