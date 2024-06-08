package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.RegistrationRepository;
@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventService eventService;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
        this.eventService = null;
    }

    // 獲取所有報名記錄
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    // 根據志工ID獲取相關的報名記錄，並使用status排序
    public List<Registration> getRegistrationsByVolunteerIdOrderByAuditStatusDesc(int volunteerId) {
        return registrationRepository.findByVolunteerIdOrderByAuditStatusDesc(volunteerId);
    }

    // 根據志工ID獲取相關的報名記錄，並使用status排序
    public List<Event> getEventsByVolunteerId(int volunteerId) {
        List<Registration> regList = registrationRepository.findByVolunteerIdOrderByAuditStatusDesc(volunteerId);
        List<Event> eventList = new ArrayList<>();
        long eventId=0;
        for (Registration reg : regList) {
            eventId = (long)reg.getEventId();
            eventService.findById(eventId).ifPresent(eventList::add);
        }
        return eventList;
    }

    // 根據活動ID獲取相關的報名記錄，並使用status排序
    public List<Registration> getRegistrationsByEventIdOrderByStatusDesc(int eventId) {
        return registrationRepository.findByEventIdOrderByAuditStatusDesc(eventId);
    }

    // 創建新的報名記錄
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    // 更新指定ID的報名記錄狀態
    public Registration updateRegistrationStatus(int id, String status) {
        return registrationRepository.findById(id)
                .map(registration -> {
                    registration.setAuditStatus(status);
                    return registrationRepository.save(registration);
                })
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
    }

    // 刪除指定ID的報名記錄
    public void deleteRegistration(int id) {
        registrationRepository.deleteById(id);
    }
    
    public Registration getRegistrationByEventIdAndVolunteerId(int eventId, int volunteerId) {
        List<Registration> reg = registrationRepository.findByEventIdAndVolunteerId(eventId, volunteerId);
        if (reg.isEmpty()) {
            return null;
        }
        return reg.get(reg.size() - 1); // 取最新一筆
    }

    public List<Registration> getRegistrationByVolunteerId(int volunteerId){
        List<Registration> reg = registrationRepository.findByVolunteerId(volunteerId);
        return reg;
    }


}
