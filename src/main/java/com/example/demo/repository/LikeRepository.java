package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    // 查找某個活動的所有收藏
    List<Like> findByEventId(int eventId);

    // 查找某個志工的所有收藏
    List<Like> findByVolunteerId(int volunteerId);
    
    Like findByVolunteerIdAndEventId(int volunteerId, int eventId);
    
    // 查找某個活動的所有收藏數
    int countByEventId(int eventId);
}

