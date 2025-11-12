package com.sensemore.tenant.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResult {

    @Data
    public static class AuthData {
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
