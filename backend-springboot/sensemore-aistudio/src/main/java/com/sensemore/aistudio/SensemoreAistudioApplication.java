package com.sensemore.aistudio;

import com.sensemore.aistudio.factory.OpenAiChatClientFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiAudioTranscriptionAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiImageAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiModerationAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@MapperScan("com.sensemore.aistudio.mapper")
@EnableAutoConfiguration(exclude = {
		OpenAiAudioSpeechAutoConfiguration.class,
		OpenAiAudioTranscriptionAutoConfiguration.class,
		OpenAiEmbeddingAutoConfiguration.class,
		OpenAiImageAutoConfiguration.class,
		OpenAiChatAutoConfiguration.class,
		OpenAiModerationAutoConfiguration.class
})
public class SensemoreAistudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensemoreAistudioApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		// 延迟到 CommandLineRunner 执行时再调用，避免在 @Bean 构造时立即发起 API 请求
		// 这样可以避免两个请求在启动时紧密连续发起而触发 Rate Limit
		return args -> {
			try {
				ChatClient chatClient = OpenAiChatClientFactory.createChatClient("openai");
				var response1 = chatClient
					.prompt("你是谁？")
					.call()
					.content();
				log.info("第一次调用成功 - response: {}", response1);
				
				// 在两次调用之间添加延迟，确保不会触发 Rate Limit
				Thread.sleep(2000); // 等待 2 秒再发起第二个请求
				
				ChatClient chatClient2 = OpenAiChatClientFactory.createChatClient("openai","gemma3:27b"); //OpenAiChatClientFactory.createChatClient("openai", "gemma3:27b", 0.7, 0.95, 5000);
				var response2 = chatClient2
					.prompt("Tell me a joke")
					.call()
					.content();
				log.info("第二次调用成功 - Answer: {}", response2);
				
			} catch (Exception e) {
				log.error("调用 AI 模型失败", e);
			}
		};
	}
}
