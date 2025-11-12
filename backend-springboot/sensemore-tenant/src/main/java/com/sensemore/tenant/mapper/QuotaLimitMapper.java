package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.QuotaLimit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 配额限制 Mapper
 */
@Mapper
public interface QuotaLimitMapper extends BaseMapper<QuotaLimit> {

    /**
     * 获取租户的配额限制
     */
    @Select("SELECT * FROM quota_limit WHERE tenant_code = #{tenantCode} LIMIT 1")
    QuotaLimit selectByTenantCode(String tenantCode);

}
