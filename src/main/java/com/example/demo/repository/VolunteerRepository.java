package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Volunteer;

import jakarta.transaction.Transactional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    List<Volunteer> findAll();

    Volunteer findByVolunteerId(int volunteerId);

    List<Volunteer> findByNameContaining(String keyword);

    Optional<Volunteer> findByEmail(String email);

    //findIntroductionByEmail
    String findIntroductionByEmail(String email);

    // 保存自我介紹
    @Modifying
    @Transactional
    @Query("UPDATE Volunteer v SET v.introduction = :introduction WHERE v.email = :account")
    int saveIntroduction(@Param("account") String account, @Param("introduction") String introduction);

}
