package com.mariakomar.events;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("integration-test")
@Testcontainers
@ContextConfiguration(initializers = {EventsApplicationTests.Initializer.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EventsApplicationTests {

    public static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.7");

    @Autowired
    private WebTestClient webTestClient;

    static {
        mariaDBContainer.start();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getEventsReturns200() {
        this.webTestClient.get().uri("/events").exchange()
                .expectStatus().isOk();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mariaDBContainer.getUsername(),
                    "spring.datasource.password=" + mariaDBContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
