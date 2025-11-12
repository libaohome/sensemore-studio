package com.sensemore.aistudio.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sensemore.aistudio.entity.AiConfig;
import com.sensemore.aistudio.mapper.AiConfigMapper;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenAiChatClientFactory {

    private static AiConfigMapper aiConfigMapper;
    private static ConcurrentHashMap <String, ChatClient> chatClientCache = new ConcurrentHashMap<>();

    private static final String CHAT_CLIENT_PREFIX = "chat_client_";
    @Autowired
    public void setAiConfigMapper(AiConfigMapper aiConfigMapper) {
        OpenAiChatClientFactory.aiConfigMapper = aiConfigMapper;
    }

    private static String getClientCacheKey(String provider, String model, double temperature, double topP){
        return String.format("%s_%s_%s_%f_%f", CHAT_CLIENT_PREFIX, provider, model, temperature, topP);
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
     * 工厂方法：创建ChatClient（使用默认模型）
     * @param provider 提供者名称（如 "openai"）
     * @return ChatClient
     */
    public static ChatClient createChatClient(String provider) {
        String clientCacheKey = getClientCacheKey(provider, getActiveAiConfig(provider).getModel(), 0, 0);
        if (chatClientCache.containsKey(clientCacheKey)) {
            return chatClientCache.get(provider);
        }
        
        OpenAiApi openAiApi = createOpenAiApi(provider);
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
            .openAiApi(openAiApi)
            .defaultOptions(OpenAiChatOptions.builder()
                    .model(getActiveAiConfig(provider).getModel())
                    .maxCompletionTokens(5000)  // ✅ 使用 maxTokens（Spring AI 内部转换）
                    .build())
            .build();
        @SuppressWarnings("null")
        ChatClient client = ChatClient.builder(openAiChatModel).build();
        chatClientCache.put(clientCacheKey, client);
        return client;
    }

    public static ChatClient createChatClient(String provider, String model) {
        String clientCacheKey = getClientCacheKey(provider, model, 0, 0);

        if (chatClientCache.containsKey(clientCacheKey)) {
            return chatClientCache.get(clientCacheKey);
        }
        OpenAiApi openAiApi = createOpenAiApi(provider);
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
            .openAiApi(openAiApi)
            .defaultOptions(OpenAiChatOptions.builder()
                    .model(model)
                    .maxCompletionTokens(5000)  // ✅ 使用 maxTokens（Spring AI 内部转换）
                    .build())
            .build();
        @SuppressWarnings("null")
        ChatClient client = ChatClient.builder(openAiChatModel).build();
        chatClientCache.put(clientCacheKey, client);
        return client;
    }

    public static ChatClient createChatClient(String provider, String model, double temperature, double topP, int maxTokens) {
        String clientCacheKey = getClientCacheKey(provider, model, temperature, topP);
        if (chatClientCache.containsKey(clientCacheKey)) {
            return chatClientCache.get(clientCacheKey);
        }
        OpenAiApi openAiApi = createOpenAiApi(provider);
        
        OpenAiChatOptions finalOptions = OpenAiChatOptions.builder()
                .model(model)
                .maxCompletionTokens(maxTokens)  // ✅ 只使用 maxTokens（Spring AI 内部会转换为 maxCompletionTokens）
                .temperature(temperature)
                .topP(topP)
                .build();
        
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(finalOptions)
                .build();
        
        @SuppressWarnings("null")
        ChatClient client = ChatClient.builder(openAiChatModel).build();
        chatClientCache.put(clientCacheKey, client);
        return client;
    }
}