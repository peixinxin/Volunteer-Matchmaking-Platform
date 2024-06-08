package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Volunteer;
import com.example.demo.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> getVolunteers() {
        return volunteerRepository.findAll();
    }

    // 根據使用者ID查找志工
    public Volunteer getVolunteerByVolunteerId(int userId) {
        return volunteerRepository.findByVolunteerId(userId);
    }

    // 根據姓名模糊查找志工
    public List<Volunteer> getVolunteersByName(String keyword) {
        return volunteerRepository.findByNameContaining(keyword);
    }

    // 根據email
    public Optional<Volunteer> getVolunteerByEmail(String email) {
        return volunteerRepository.findByEmail(email);
    }
    
    // 創建志工
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    // 更新志工信息
    public Volunteer updateVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    // 從數據源中獲取自我介紹
    public String getIntroduction(String account) {
        return volunteerRepository.findIntroductionByEmail(account);
    }

    // 更新志工簡介
    public boolean updateVolunteerIntroduction(String email, String introduction) {
        return volunteerRepository.findByEmail(email)
                .map(volunteer -> {
                    volunteer.setIntroduction(introduction);
                    volunteerRepository.save(volunteer);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("Volunteer not found with email " + email));
    }

    //更新志工部分資料
    public boolean updateVolunteerSetting(int userId, String gender, String name, int age) {
        return volunteerRepository.findById(userId)
                .map(volunteer -> {
                    volunteer.setName(name);
                    volunteer.setGender(gender);
                    volunteer.setAge(age);
                    volunteerRepository.save(volunteer);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("Volunteer not found with userId " + userId));
    }

    // 刪除志工
    public void deleteVolunteer(int id) {
        volunteerRepository.deleteById(id);
    }
}
