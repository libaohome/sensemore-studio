package com.sensemore.tenant.convertor;

import com.sensemore.tenant.dto.TenantResponse;
import com.sensemore.tenant.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户实体与DTO转换器（使用MapStruct实现）
 * MapStruct会在编译时生成实现类，性能优于反射
 */
@Mapper(componentModel = "spring")
public interface TenantConvertor {

    /**
     * 获取转换器实例（使用Spring组件模型时，由Spring管理）
     */
    TenantConvertor INSTANCE = Mappers.getMapper(TenantConvertor.class);

    /**
     * 将 Tenant 实体转换为 TenantResponse DTO
     */
    TenantResponse toResponse(Tenant tenant);

    /**
     * 将 Tenant 列表转换为 TenantResponse 列表
     * MapStruct 会自动处理列表转换
     */
    List<TenantResponse> toResponseList(List<Tenant> tenants);
}

