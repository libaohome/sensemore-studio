package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.mapper.TenantUserMapper;
import org.springframework.stereotype.Service;

@Service
public class TenantUserService extends ServiceImpl<TenantUserMapper, TenantUser> {
    public TenantUser getByTenantCodeAndUsername(String tenantCode, String username) {
        return lambdaQuery().eq(TenantUser::getTenantCode, tenantCode)
                            .eq(TenantUser::getUsername, username)
                            .one();
    }
}
