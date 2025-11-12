package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.UserToken;
import com.sensemore.tenant.mapper.UserTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserTokenService extends ServiceImpl<UserTokenMapper, UserToken> {

    /**
     * 根据token获取Token记录
     */
    public UserToken getByToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        return lambdaQuery().eq(UserToken::getToken, token).one();
    }

    /**
     * 判断token是否已过期
     */
    public boolean isExpired(UserToken tokenRecord) {
        return Optional.ofNullable(tokenRecord)
                .map(UserToken::getExpireAt)
                .map(expireAt -> expireAt.isBefore(LocalDateTime.now()))
                .orElse(false);
    }
}
