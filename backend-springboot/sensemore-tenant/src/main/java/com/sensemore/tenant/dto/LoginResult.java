package com.sensemore.tenant.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResult<T> extends ResultDto<T> {

    @Data
    public static class LoginData {
        private String token;
        private String refreshToken;
        private UserInfo userInfo;
    }

    @Data
    public static class UserInfo {
        private String userId;
        private String username;
        private String avatar;
        private String email;
        private String phone;
              private String tenantCode;
              private String tenantName;
         }
}
