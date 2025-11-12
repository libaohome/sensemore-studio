package com.sensemore.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sensemore.tenant.entity.ApiCallLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * API 调用日志 Mapper
 */
@Mapper
public interface ApiCallLogMapper extends BaseMapper<ApiCallLog> {

}
