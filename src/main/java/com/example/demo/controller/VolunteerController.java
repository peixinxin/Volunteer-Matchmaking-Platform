
package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Volunteer;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.service.VolunteerService;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVolunteerById(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Volunteer volunteer = volunteerService.getVolunteerByVolunteerId(id);
            if (volunteer != null) {
                response.put("volunteer", volunteer);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Volunteer not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error fetching volunteer");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/introduction/{volunteerId}")
    public ResponseEntity<Volunteer> getIntroductionById(@PathVariable("volunteerId") Integer id) {
        Volunteer volunteer = volunteerService.getVolunteerByVolunteerId(id);
        return ResponseEntity.ok(volunteer);
    }

    // GET方法來獲取自我介紹
    @GetMapping("/{account}/introduction")
    public ResponseEntity<VolunteerIntroductionResponse> getIntroduction(@PathVariable("account") String account) {
        String introduction = volunteerService.getIntroduction(account);
        VolunteerIntroductionResponse response = new VolunteerIntroductionResponse(introduction);
        return ResponseEntity.ok(response);
    }

    // POST方法來保存自我介紹
    @PostMapping("/{account}/introduction")
    public ResponseEntity<GenericResponse> saveIntroduction(
            @PathVariable("account") String account,
            @RequestBody VolunteerIntroductionRequest request) {
        boolean success = volunteerService.updateVolunteerIntroduction(account, request.getIntroduction());
        if (success) {
            return ResponseEntity.ok(new GenericResponse(true));
        } else {
            return ResponseEntity.status(404).body(new GenericResponse(false));
        }
    }

    // POST方法來保存志工資料
    @PostMapping("/save/{volunteerId}")
    public ResponseEntity<GenericResponse> saveVolunteer(
            @PathVariable("volunteerId") int volunteerId,
            @RequestBody VolunteerSettingRequest volunteerSetting) {
            boolean success = volunteerService.updateVolunteerSetting(volunteerId, volunteerSetting.getGender(), volunteerSetting.getName(), volunteerSetting.getAge());
            if (success) {
                return ResponseEntity.ok(new GenericResponse(true));
            } else {
                return ResponseEntity.status(404).body(new GenericResponse(false));
            }
    }

    // 响应数据类
    public static class VolunteerIntroductionResponse {
        private String introduction;

        public VolunteerIntroductionResponse(String introduction) {
            this.introduction = introduction;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
    public static class VolunteerSettingRequest {
        private String name;
        private String gender;
        private Integer age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getGender() {
            return gender;
        }
        public void setGender(String gender){
            this.gender = gender;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        
    }

    public static class VolunteerIntroductionRequest {
        private String introduction;

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }

    public static class GenericResponse {
        private boolean success;

        public GenericResponse(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
