package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.AlertRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 告警规则 Mapper
 */
@Mapper
public interface AlertRuleMapper extends BaseMapper<AlertRule> {

    /**
     * 获取租户启用的所有告警规则
     */
    @Select("SELECT * FROM alert_rule WHERE tenant_code = #{tenantCode} AND status = 1")
    List<AlertRule> selectByTenantCodeAndActive(String tenantCode);

    /**
     * 按规则类型获取租户的告警规则
     */
    @Select("SELECT * FROM alert_rule WHERE tenant_code = #{tenantCode} AND rule_type = #{ruleType} AND status = 1")
    List<AlertRule> selectByTenantAndRuleType(String tenantCode, String ruleType);

}
