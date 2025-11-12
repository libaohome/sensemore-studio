package com.sensemore.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录Token表
 */
@Data
@TableName("user_token")
@Schema(name = "UserToken", description = "用户登录Token表")
public class UserToken implements Serializable {
    @TableId(type = IdType.AUTO)
    @Schema(description = "Token主键ID")
    private Long id;

    @TableField
    @Schema(description = "用户ID")
    private Long userId;

    @TableField
    @Schema(description = "租户业务代码")
    private String tenantCode;

    @TableField
    @Schema(description = "Token值")
    private String token;

    @TableField
    @Schema(description = "刷新Token值")
    private String refreshToken;

    @TableField
    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    @TableField
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
