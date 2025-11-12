package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.ModelConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 模型配置 Mapper
 */
@Mapper
public interface ModelConfigMapper extends BaseMapper<ModelConfig> {

    /**
     * 获取租户的默认模型配置
     */
    @Select("SELECT * FROM model_config WHERE tenant_code = #{tenantCode} AND is_default = 1 AND status = 1 LIMIT 1")
    ModelConfig selectDefaultByTenant(String tenantCode);

    /**
     * 获取租户的所有启用配置
     */
    @Select("SELECT * FROM model_config WHERE tenant_code = #{tenantCode} AND status = 1 ORDER BY is_default DESC")
    List<ModelConfig> selectByTenantCodeAndActive(String tenantCode);

    /**
     * 获取特定AI厂商配置的所有模型配置
     */
    @Select("SELECT * FROM model_config WHERE provider_config_id = #{providerConfigId} AND status = 1")
    List<ModelConfig> selectByProviderConfigId(Long providerConfigId);

}
