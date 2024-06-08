
package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Registration;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.service.RegistrationService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/fororgcheck")
    public List<Registration> getRegistrationsByOrganizationId(@RequestParam(name = "organizationId") int organizationId) {
        List<Long> eventIds = eventRepository.findEventIdsByHostId(organizationId);
        return registrationRepository.findByEventIdIn(eventIds);
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addRegistration(
        @RequestBody AddingRegRequest addingRegRequest) {
            try {
                Registration exist = registrationService.getRegistrationByEventIdAndVolunteerId(addingRegRequest.getEventId(),addingRegRequest.getVolunteerId());
                if (exist==null){
                    Registration reg = new Registration();
                    reg.setEventId(addingRegRequest.getEventId());
                    reg.setVolunteerId(addingRegRequest.getVolunteerId());
                    reg.setAuditStatus("審核中");
                    Registration newReg = registrationRepository.save(reg);
                    if (newReg != null) {
                        return ResponseEntity.ok(newReg);
                    }else{
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("內部伺服器錯誤");
                    }
                }else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("已申請過此活動，請勿重複申請。");
                }
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("內部伺服器錯誤");
            }
    }
    //刪除報名
    @PostMapping("/remove")
    public ResponseEntity<Void> removeRegistration(
        @RequestBody AddingRegRequest addingRegRequest) {
        try {
            Registration registration = registrationService.getRegistrationByEventIdAndVolunteerId(addingRegRequest.getEventId(), addingRegRequest.getVolunteerId());
            registrationService.deleteRegistration(registration.getRegistrationId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /*
    //新增報名
    @PostMapping("/add")
    public ResponseEntity<Registration> addRegistration(
        @RequestBody AddingRegRequest addingRegRequest) {
        Registration reg = new Registration();
        reg.setEventId(addingRegRequest.getEventId());
        reg.setVolunteerId(addingRegRequest.getVolunteerId());
        reg.setAuditStatus("審核中");
        Registration newReg = registrationRepository.save(reg);
        if (newReg != null) {
            return ResponseEntity.ok(newReg);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
         */
    public static class AddingRegRequest {
        private Integer volunteerId;
        private Integer eventId;
        public Integer getVolunteerId() {
            return volunteerId;
        }
        public void setVolunteerId(Integer volunteerId) {
            this.volunteerId = volunteerId;
        }
        public Integer getEventId() {
            return eventId;
        }
        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }
    }
    //檢查是否報名過
    @GetMapping("find/{volunteerId}/{eventId}")
    public ResponseEntity<GenericResponse> getRegistrationByVAndEId(@PathVariable("volunteerId") Integer volunteerId, @PathVariable("eventId") Integer eventId) {
        try {
        Registration registration = registrationService.getRegistrationByEventIdAndVolunteerId(eventId, volunteerId);
            if (registration != null) {
                return ResponseEntity.ok(new GenericResponse(true));    //為true代表有找到
            } else {
                return ResponseEntity.ok(new GenericResponse(false));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new GenericResponse(false));   //為false代表沒找到
        }
    }
    public class GenericResponse {
        private boolean success;
    
        public GenericResponse(boolean success) {
            this.success = success;
        }
    
        // Getter and Setter
        public boolean isSuccess() {
            return success;
        }
    
        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<Map<String, String>> approveRegistration(@RequestBody ApprovalRequest approvalRequest) {
        String status = approvalRequest.getStatus();
        int registrationId = approvalRequest.getRegistrationId();

        Map<String, String> response = new HashMap<>();
        try {
            registrationService.updateRegistrationStatus(registrationId, status);
            response.put("message", "审核成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "审核失败");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    public static class ApprovalRequest {
        private int registrationId;
        private String status;

        // Getters and Setters
        public int getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(int registrationId) {
            this.registrationId = registrationId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}