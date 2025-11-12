package com.sensemore.tenant.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CaptchaResult<T> {

    @Data
    public static class CaptchaData {

        public CaptchaData() {
        }

        public CaptchaData(String captchaId, String captchaImg) {
            this.captchaId = captchaId;
            this.captchaImg = captchaImg;
        }

        private String captchaId;
        private String captchaImg;
    }
}
