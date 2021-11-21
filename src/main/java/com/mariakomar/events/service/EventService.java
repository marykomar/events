package com.mariakomar.events.service;

import com.mariakomar.events.model.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    List<Event> getNotExpiredEvents();

    List<Event> getEventsByText(String text);
}
