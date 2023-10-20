package tn.esprit.Services;

import tn.esprit.Entities.Event;
import tn.esprit.Entities.Request;
import tn.esprit.Entities.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface IEventService {

    Event addEvent (Event d);
    Event updateEvent (Event d);
    void deleteEvent (Integer idEvent);



    List<Event> retrieveAllEvents();
    Event retrieveEvent(Integer idEvent);


    List<Event> retrieveEventsByLocation(String location);

    List<Event> retrieveEventsByName(String name);

    List<Event> retrieveEventsByTimeRange(LocalDateTime beginsAtEvent, LocalDateTime endsAtEvent);
}
