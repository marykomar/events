package com.mariakomar.events.controller;

import com.mariakomar.events.model.Event;
import com.mariakomar.events.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("events")
    public List<Event> allEvents(@RequestParam(required = false, name = "status") String status,
                                 @RequestParam(required = false, name = "text") String text) {
        // todo
        if (status != null && status.equals("not-expired")) {
            return eventService.getNotExpiredEvents();
        } else if (text != null) {
            return eventService.getEventsByText(text);
        }
        return eventService.getAllEvents();
    }
}
