package com.sensemore.aistudio.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sensemore.aistudio.entity.AiConfig;
import com.sensemore.aistudio.mapper.AiConfigMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatClientFactory {

    private static AiConfigMapper aiConfigMapper;

    @Autowired
    public void setAiConfigMapper(AiConfigMapper aiConfigMapper) {
        ChatClientFactory.aiConfigMapper = aiConfigMapper;
    }

    /**
     * 模拟方法：从数据库获取活跃的AI配置
     * @return AiConfig
     */
    private static AiConfig getActiveAiConfig(String provider) {
        QueryWrapper<AiConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("provider",provider).eq("is_active", true).last("LIMIT 1");
        return aiConfigMapper.selectOne(queryWrapper);
    }

    /**
     * 工厂方法：创建OpenAiApi
     * @return OpenAiApi
     */
    private static OpenAiApi createOpenAiApi(String provider) {
        AiConfig config = getActiveAiConfig(provider);
        if (config == null) {
            throw new RuntimeException("No active AI configuration found");
        }
        return OpenAiApi.builder()
                .apiKey(config.getApiKey())
                .baseUrl(config.getBaseUrl())
                .build();
    }

    /**
     * 工厂方法：创建OpenAiChatModel
     * @param openAiApi OpenAiApi实例
     * @return OpenAiChatModel
     */
    private static OpenAiChatModel createOpenAiChatModel(OpenAiApi openAiApi, String model) {
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(model)
                        .build())
                .build();
    }

    /**
     * 工厂方法：创建ChatClient
     * @param openAiChatModel OpenAiChatModel实例
     * @return ChatClient
     */
    public static ChatClient createChatClient(String provider) {
        OpenAiApi openAiApi = createOpenAiApi(provider);
        OpenAiChatModel openAiChatModel = createOpenAiChatModel(openAiApi, getActiveAiConfig(provider).getModel());

        return ChatClient.builder(openAiChatModel).build();
    }

}
