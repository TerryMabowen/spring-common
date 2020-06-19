package com.mbw.common.test.bkdl;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-17 18:51
 */
@Data
@Builder
@ToString
public class ParamData {
    private String accountNo;

    private String time;

    private Integer type;
}
