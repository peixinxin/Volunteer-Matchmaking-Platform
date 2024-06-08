package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findAll();

    List<Registration> findByVolunteerId(int volunteerId);

    List<Registration> findByEventId(int eventId);

    List<Registration> findByAuditStatus(String auditStatus);
    // 新增方法：根據 eventId 查找並按 status 排序
    List<Registration> findByEventIdOrderByAuditStatusDesc(int eventId); // 降序排序

    // 新增方法：根據 volunteerId
    List<Registration> findByVolunteerIdOrderByAuditStatusDesc(int eventId); // 降序排序

    List<Registration> findByEventIdAndVolunteerId(int eventId, int volunteerId);

    List<Registration> findByEventIdIn(List<Long> eventIds);
}
