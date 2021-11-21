package com.mariakomar.events.service;

import com.mariakomar.events.model.Event;
import com.mariakomar.events.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private DefaultEventService eventService;

    @Test
    void getAllNotExpiredEventsReturnsCorrectEvents() {
        List<Event> events = new ArrayList<>();
        Event event1 = new Event();
        event1.setId(1L);
        event1.setNotificationTime(OffsetDateTime.now().minus(1, ChronoUnit.DAYS));
        events.add(event1);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setNotificationTime(OffsetDateTime.now().plus(1, ChronoUnit.DAYS));
        events.add(event2);

        Mockito.when(eventRepository.findAll()).thenReturn(events);

        List<Event> allNotExpiredEvents = eventService.getNotExpiredEvents();

        Assertions.assertEquals(1, allNotExpiredEvents.size());
    }
}
