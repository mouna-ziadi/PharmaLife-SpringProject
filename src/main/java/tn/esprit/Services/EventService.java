package tn.esprit.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.*;
import tn.esprit.Repositories.EventRepository;
import tn.esprit.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class EventService implements IEventService {
    EventRepository eventRepository;
    UserRepository userRepository;
    @Override
    public Event addEvent(Event d) {

        return eventRepository.save(d);
    }

    @Override
    public Event updateEvent(Event d) {
        return eventRepository.save(d);
    }

    @Override
    public void deleteEvent(Integer idEvent) {
        eventRepository.deleteById(idEvent);
    }



    @Override
    public List<Event> retrieveAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event retrieveEvent(Integer idEvent) {
        return eventRepository.findById(idEvent).get();
    }

    @Override
    public List<Event> retrieveEventsByLocation(String location) {
        return eventRepository.findByLocationEvent(location);
    }

    @Override
    public List<Event> retrieveEventsByName(String name) {
        return eventRepository.findByNameEvent(name);
    }

    @Override
    public List<Event> retrieveEventsByTimeRange(LocalDateTime beginsAtEvent, LocalDateTime endsAtEvent) {
        return eventRepository.findByBeginsAtEventBetween(beginsAtEvent, endsAtEvent);
    }



}
