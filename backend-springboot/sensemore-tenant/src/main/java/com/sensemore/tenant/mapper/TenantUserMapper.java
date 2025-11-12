package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.TenantUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantUserMapper extends BaseMapper<TenantUser> {
}
