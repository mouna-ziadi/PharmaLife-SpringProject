package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Event;
import tn.esprit.Services.IEventService;


import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/events")

public class EventRestController {
    IEventService iEventService;

    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody Event d){
        Event event = iEventService.addEvent(d);
        return event;
    }

    @PutMapping("/updateEvent")
    public Event updateEvent(@RequestBody Event d){
        Event event = iEventService.updateEvent(d);
        return event;
    }

    @DeleteMapping("deleteEvent/{id_event}")
    public void deleteEvent(@PathVariable("id_event") Integer IdEvent){
        iEventService.deleteEvent(IdEvent);
    }




    @GetMapping("/retrieveAllEvents")
    public List<Event> retrieveAllEvents(){
        List<Event> listEvents = iEventService.retrieveAllEvents();
        return listEvents;
    }

    @GetMapping("/retrieveEvent/{id_event}")
    public Event retrieveEvent(@PathVariable("id_event")Integer IdEvent){
        return iEventService.retrieveEvent(IdEvent);
    }
    @GetMapping("/location/{location}")
    public List<Event> getEventsByLocation(@PathVariable String location) {
        return iEventService.retrieveEventsByLocation(location);
    }

    @GetMapping("/name/{name}")
    public List<Event> getEventsByName(@PathVariable String name) {
        return iEventService.retrieveEventsByName(name);
    }
    @GetMapping("/eventsByTimeRange")
    public List<Event> getEventsByTimeRange(@RequestParam("beginsAtEvent") String beginsAtEvent,
                                            @RequestParam("endsAtEvent") String endsAtEvent) {
        LocalDateTime beginsAt = LocalDateTime.parse(beginsAtEvent);
        LocalDateTime endsAt = LocalDateTime.parse(endsAtEvent);
        return iEventService.retrieveEventsByTimeRange(beginsAt, endsAt);
    }

}
