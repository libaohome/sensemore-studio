package com.sensemore.tenant.convertor;

import com.sensemore.tenant.dto.UserResponse;
import com.sensemore.tenant.entity.TenantUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户实体与DTO转换器（使用MapStruct实现）
 * MapStruct会在编译时生成实现类，性能优于反射
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

    /**
     * 获取转换器实例（使用Spring组件模型时，由Spring管理）
     */
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将 TenantUser 实体转换为 UserResponse DTO
     * 自动排除 password 字段（因为 UserResponse 中没有该字段，MapStruct会自动忽略）
     */
    UserResponse toResponse(TenantUser user);

    /**
     * 将 TenantUser 列表转换为 UserResponse 列表
     * MapStruct 会自动处理列表转换
     */
    List<UserResponse> toResponseList(List<TenantUser> users);
}

