package tn.esprit.Repositories;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.Entities.Event;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EventRepository  extends CrudRepository<Event, Integer>  {


    List<Event> findByLocationEvent(String location);

    List<Event> findByNameEvent(String name);

    List<Event> findByBeginsAtEventBetween(LocalDateTime beginsAtEvent, LocalDateTime endsAtEvent);
}
