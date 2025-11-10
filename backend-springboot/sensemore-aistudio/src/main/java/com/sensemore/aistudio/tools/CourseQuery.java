package com.sensemore.aistudio.tools;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
public class CourseQuery {
    @ToolParam(required = false, description = "课程类型：编程、设计、自媒体、其它")
    private String type;

    @ToolParam(required = false, description = "学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上")
    private Integer edu;
}

