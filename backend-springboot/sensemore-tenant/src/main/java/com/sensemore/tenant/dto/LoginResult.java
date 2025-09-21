package com.sensemore.tenant.dto;

import lombok.Data;

@Data
public class LoginResult<LoginData> extends ResultDto<LoginData> {

    @Data
    public static class LoginData{
        private String token;
        private UserInfo userInfo;
    }

    @Data
    public static class UserInfo{
        private Integer userId;
        private String username;
        private String avatar;
        private String email;
        private String phone;
    }
}
