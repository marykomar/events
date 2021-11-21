package com.mariakomar.events.repository;

import com.mariakomar.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e where e.text like %:text%")
    List<Event> findByTextLike(@Param("text") String text);
}
