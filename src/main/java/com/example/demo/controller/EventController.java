package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;
import com.example.demo.service.RegistrationService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/clickdesc")
    public ResponseEntity<List<Event>> findAllByOrderByClickDesc() {
        String status = "審核通過"; //  替換為需要審核的狀態
        List<Event> events = eventRepository.findAllByOrderByClickDescAndSuperviseStatus(status);
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(name = "minFee", required = false) Integer minFee,
            @RequestParam(name = "maxFee", required = false) Integer maxFee,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "searchTerm", required = false) String searchTerm
    ) {
        try {
            List<Event> events = eventService.findFilteredEvents(minFee, maxFee, startDate, endDate, searchTerm);
            if (events.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }
    @GetMapping("/byhost/{host}")
    public ResponseEntity<List<Event>> getEventsByHost(@PathVariable("host") Integer host) {
        List<Event> events = eventService.findEventsByHost(host);
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable("id") Long id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 更新事件的控制器方法
    @PostMapping("/update/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String id, @RequestBody Event event) {
        Long eventId;
        eventId = Long.parseLong(id);

        return eventService.findById(eventId)
            .map(existingEvent -> {
                existingEvent.setName(event.getName());
                existingEvent.setDescription(event.getDescription());
                existingEvent.setLocation(event.getLocation());
                existingEvent.setStartDate(event.getStartDate());
                existingEvent.setCost(event.getCost());
                existingEvent.setRemark(event.getRemark());
                if (event.getReviewReason() != null) {
                    existingEvent.setReviewReason(event.getReviewReason());
                } else {
                    existingEvent.setReviewReason("Default review reason"); // 設置默認值
                }
                Event updatedEvent = eventService.updateEvent(existingEvent);
                return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.ok(savedEvent);
    }

    @GetMapping("/volunteer/{volunteerId}") // 先撈出registration(s)
    public ResponseEntity<List<Registration>> getEventsByVolunteerId(@PathVariable("volunteerId") int volunteerId) {
        List<Registration> reg = registrationService.getRegistrationByVolunteerId(volunteerId);
        if (!reg.isEmpty()) {
            return ResponseEntity.ok(reg);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/regEvent/{regEventId}") //根據eventId回傳event
    public ResponseEntity<Event> getEventsById(@PathVariable("regEventId") int regEventId) {
        long eventId = (long)regEventId;
        Optional<Event> event = eventService.findById(eventId);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

