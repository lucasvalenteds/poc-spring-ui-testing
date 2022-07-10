package com.example;

import com.devskiller.jfairy.Fairy;
import com.example.link.Link;
import com.example.link.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(LinkRepository linkRepository) {
        final var fairy = Fairy.create();
        return args -> {
            linkRepository.deleteAll();

            for (int i = 0; i < 10; i++) {
                final var link = new Link();
                link.setUrl("https://example.com/" + UUID.randomUUID());
                link.setTitle(fairy.textProducer().sentence());
                link.setDescription(fairy.textProducer().paragraph());

                final var linkSaved = linkRepository.save(link);
                LOGGER.info("Link created (link={})", linkSaved);
            }
        };
    }
}
