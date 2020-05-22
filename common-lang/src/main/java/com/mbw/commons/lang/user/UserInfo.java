package com.mbw.commons.lang.user;

import lombok.Data;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-21 17:27
 */
@Data
public class UserInfo {
    private Long userId;

    private String username;

    private String nickName;

    private String realName;
}
