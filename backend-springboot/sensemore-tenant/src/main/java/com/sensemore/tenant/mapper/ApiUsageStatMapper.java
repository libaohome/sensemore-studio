package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.ApiUsageStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * API 调用量统计 Mapper
 */
@Mapper
public interface ApiUsageStatMapper extends BaseMapper<ApiUsageStat> {

    /**
     * 获取租户指定日期的统计数据
     */
    @Select("SELECT * FROM api_usage_stat WHERE tenant_id = #{tenantId} AND date_key = #{dateKey}")
    List<ApiUsageStat> selectByTenantAndDate(Long tenantId, LocalDate dateKey);

    /**
     * 获取租户指定日期范围内的统计数据
     */
    @Select("SELECT * FROM api_usage_stat WHERE tenant_id = #{tenantId} AND date_key >= #{startDate} AND date_key <= #{endDate}")
    List<ApiUsageStat> selectByTenantAndDateRange(Long tenantId, LocalDate startDate, LocalDate endDate);

}
