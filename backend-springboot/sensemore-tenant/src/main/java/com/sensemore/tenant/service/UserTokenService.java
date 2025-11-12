package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.UserToken;
import com.sensemore.tenant.mapper.UserTokenMapper;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService extends ServiceImpl<UserTokenMapper, UserToken> {
}
