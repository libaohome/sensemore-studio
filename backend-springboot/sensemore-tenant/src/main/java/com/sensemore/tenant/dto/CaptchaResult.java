package com.sensemore.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CaptchaResult<CaptchaData> extends ResultDto<CaptchaData> {


    @Data
    public static class CaptchaData {

        public CaptchaData(String captchaId, String captchaImage) {
            this.captchaId = captchaId;
            this.captchaImage = captchaImage;
        }
        private String captchaId;
        private String captchaImage;
    }
}
