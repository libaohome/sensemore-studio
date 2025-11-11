package com.sensemore.aistudio;

import com.sensemore.aistudio.factory.ChatClientFactory;
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
		ChatClient chatClient = ChatClientFactory.createChatClient("openai");
		return args -> {
			var response = chatClient
				.prompt("Tell me a joke")
				.call()
				.content();
			log.info("Answer: " + response);
		};
	}

}
