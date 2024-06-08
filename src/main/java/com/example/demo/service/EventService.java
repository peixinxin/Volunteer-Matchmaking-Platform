package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // 獲取所有事件
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    // 添加新事件
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    // 更新事件
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    // 删除事件
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<Event> findEventsByHost(Integer host) {
        return eventRepository.findByHost(host);
    }

    public List<Event> findFilteredEvents(Integer minFee, Integer maxFee, LocalDate startDate, LocalDate endDate, String searchTerm) {
        return eventRepository.findFilteredEvents(minFee, maxFee, startDate, endDate, searchTerm);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
    
    
}
