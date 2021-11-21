package com.mariakomar.events.repository;


import com.mariakomar.events.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers
@ContextConfiguration(initializers = {EventRepositoryTest.Initializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    public static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.7");

    static {
        mariaDBContainer.start();
    }

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Sql("/createEvents.sql")
    void testGetEventsByText() {
        List<Event> events = eventRepository.findByTextLike("test");

        assertEquals(1, events.size());
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
