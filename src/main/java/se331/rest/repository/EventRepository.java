package se331.rest.repository;

import org.springframework.data.repository.CrudRepository;
import se331.rest.entity.Event;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAll();
}
