package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.AiProviderConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AI厂商配置 Mapper
 */
@Mapper
public interface AiProviderConfigMapper extends BaseMapper<AiProviderConfig> {

    /**
     * 获取租户的所有启用的AI厂商配置
     */
    @Select("SELECT * FROM ai_provider_config WHERE tenant_code = #{tenantCode} AND status = 1 ORDER BY priority ASC")
    List<AiProviderConfig> selectByTenantCodeAndActive(String tenantCode);

    /**
     * 获取指定租户和厂商名的配置
     */
    @Select("SELECT * FROM ai_provider_config WHERE tenant_code = #{tenantCode} AND provider_name = #{providerName} AND status = 1 LIMIT 1")
    AiProviderConfig selectByTenantAndProvider(String tenantCode, String providerName);

}
