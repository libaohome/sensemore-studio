package com.sensemore.tenant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * 短信验证码服务（简化版，使用内存存储）
 * 生产环境建议使用Redis等缓存服务
 */
@Slf4j
@Service
public class SmsCodeService {
    
    // 使用内存存储验证码，key: tenantCode_phone, value: code
    private final Map<String, String> codeStorage = new ConcurrentHashMap<>();
    
    // 验证码有效期（毫秒），默认5分钟
    private static final long CODE_EXPIRE_TIME = 5 * 60 * 1000;
    
    // 验证码存储结构：code|timestamp
    private final Map<String, Long> codeTimestamp = new ConcurrentHashMap<>();
    
    /**
     * 生成6位数字验证码
     */
    private String generateCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
    
    /**
     * 发送短信验证码
     * @param tenantCode 租户代码
     * @param phone 手机号
     * @return 验证码（实际生产环境不应返回）
     */
    public String sendSmsCode(String tenantCode, String phone) {
        String key = tenantCode + "_" + phone;
        String code = generateCode();
        
        // 存储验证码和时间戳
        codeStorage.put(key, code);
        codeTimestamp.put(key, System.currentTimeMillis());
        
        log.info("发送短信验证码: tenantCode={}, phone={}, code={}", tenantCode, phone, code);
        
        // 实际生产环境应该调用短信服务商API发送短信
        // 这里仅做演示，返回验证码用于测试
        
        return code;
    }
    
    /**
     * 验证短信验证码
     * @param tenantCode 租户代码
     * @param phone 手机号
     * @param code 验证码
     * @return 是否验证通过
     */
    public boolean verifyCode(String tenantCode, String phone, String code) {
        String key = tenantCode + "_" + phone;
        String storedCode = codeStorage.get(key);
        Long timestamp = codeTimestamp.get(key);
        
        if (storedCode == null || timestamp == null) {
            log.warn("验证码不存在或已过期: tenantCode={}, phone={}", tenantCode, phone);
            return false;
        }
        
        // 检查是否过期
        if (System.currentTimeMillis() - timestamp > CODE_EXPIRE_TIME) {
            log.warn("验证码已过期: tenantCode={}, phone={}", tenantCode, phone);
            codeStorage.remove(key);
            codeTimestamp.remove(key);
            return false;
        }
        
        // 验证码匹配
        boolean isValid = storedCode.equals(code);
        if (isValid) {
            // 验证成功后删除验证码
            codeStorage.remove(key);
            codeTimestamp.remove(key);
            log.info("验证码验证成功: tenantCode={}, phone={}", tenantCode, phone);
        } else {
            log.warn("验证码错误: tenantCode={}, phone={}", tenantCode, phone);
        }
        
        return isValid;
    }
}

