package com.sensemore.aistudio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ai_config")
public class AiConfig {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String provider;
    private String apiKey;
    private String baseUrl;
    private String model;
    private Boolean isActive;
}
