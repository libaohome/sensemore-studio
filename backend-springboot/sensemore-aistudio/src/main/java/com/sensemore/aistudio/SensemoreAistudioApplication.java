package com.sensemore.aistudio;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SensemoreAistudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensemoreAistudioApplication.class, args);
	}

	@Bean
	public OpenAiApi openAiApi() {
		return OpenAiApi.builder()
				.apiKey("115925abb19ec543cdcbe8af4506ff463ea2b5e8")
				.baseUrl("https://api-77aaidn1l8c5b7xa.aistudio-app.com/")
				.build();
	}

	@Bean
	public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
		return OpenAiChatModel.builder()
				.openAiApi(openAiApi)
				.defaultOptions(OpenAiChatOptions.builder()
						.model("gemma3:27b")
						.build())
				.build();
	}

	@Bean
	public ChatClient chatClient(OpenAiChatModel model) {
		return ChatClient.builder(model).build();
	}

	@Bean
	public CommandLineRunner run(ChatClient chatClient) {
	return args -> {
		var response = chatClient
			.prompt("Tell me a joke")
			.call()
			.content();
		log.info("Answer: " + response);
	};
	}

}
