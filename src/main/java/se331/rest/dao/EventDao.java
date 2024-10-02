package se331.rest.dao;

import org.springframework.data.domain.Page;
import se331.rest.entity.Event;

// Data Access Object for Event
public interface EventDao {
    Integer getEventSize();
    Page<Event> getEvents(Integer pageSize, Integer page);
    Event getEvent(Long id);
    Event save(Event event);
}
