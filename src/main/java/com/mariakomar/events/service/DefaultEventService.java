package com.mariakomar.events.service;

import com.mariakomar.events.model.Event;
import com.mariakomar.events.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultEventService implements EventService {

    private final EventRepository eventRepository;

    public DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getNotExpiredEvents() {
        return eventRepository.findAll().stream()
                .filter(event -> event.getNotificationTime().isAfter(OffsetDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsByText(String text) {
        return eventRepository.findByTextLike(text);
    }
}
