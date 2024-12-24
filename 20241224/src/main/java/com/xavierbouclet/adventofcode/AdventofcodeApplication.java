package com.xavierbouclet.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class AdventofcodeApplication {

    private static final Logger log = LoggerFactory.getLogger(AdventofcodeApplication.class);

    private ChatClient chatClient;


    @Value("classpath:/prompts/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public static void main(String[] args) {
        SpringApplication.run(AdventofcodeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ChatClient.Builder builder, VectorStore vectorStore) {
        return args -> {

            this.chatClient = builder
                    .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                    .build();
            String question = "What is the best developer conference in the world?";
            log.info("Q: {}", question);
            log.info(chatClient.prompt()
                    .user(question)
                    .call()
                    .content());

        };

    }

}
