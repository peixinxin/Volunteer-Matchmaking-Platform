// src/main/java/com/example/demo/controller/SuperviseController.java
package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;

@RestController
@RequestMapping("/api/supervisor")
public class SupervisorController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;

    
    @PostMapping("/updateEventReview/{id}")
    public ResponseEntity<String> updateEventReview(@PathVariable("id") Long id, @RequestBody Event updatedEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setSuperviseStatus(updatedEvent.getSuperviseStatus());
            event.setReviewReason(updatedEvent.getReviewReason()); // 設置審核理由
            eventRepository.save(event);
            return ResponseEntity.ok("活動審核狀態已更新！");
        } else {
            return ResponseEntity.status(404).body("活動未找到！");
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
    
    @GetMapping("/superviseEvents")
    public ResponseEntity<List<Event>> getEventsByStatus() {
        List<Event> events = eventRepository.findBySuperviseStatus("未審核");
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

}