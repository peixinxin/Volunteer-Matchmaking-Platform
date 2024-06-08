package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Like;
import com.example.demo.repository.LikeRepository;

@Service
public class LikeService {
    private LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // 獲取所有收藏
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    // 根據ID查找收藏
    public Optional<Like> getLikeById(int id) {
        return likeRepository.findById(id);
    }

    // 創建新的收藏
    public Like createLike(Like like) {
        return likeRepository.save(like);
    }

    public Like createLike(int volunteerId, int eventId) {
        Like like = new Like(volunteerId, eventId);
        return likeRepository.save(like);
    }

    // 更新收藏信息
    public Like updateLike(int id, Like likeDetails) {
        return likeRepository.findById(id)
                .map(like -> {
                    like.setEventId(likeDetails.getEventId());
                    like.setVolunteerId(likeDetails.getVolunteerId());
                    return likeRepository.save(like);
                })
                .orElseThrow(() -> new RuntimeException("Like not found with id " + id));
    }

    // 刪除收藏
    public void deleteLike(int id) {
        likeRepository.deleteById(id);
    }

    // 根據活動ID查找所有收藏
    public List<Like> getLikesByEventId(int eventId) {
        return likeRepository.findByEventId(eventId);
    }

    // 根據志工ID查找所有收藏
    public List<Like> getLikesByVolunteerId(Integer volunteerId) {
        return likeRepository.findByVolunteerId(volunteerId);
    }

    public Like getLikeByVAndEId(int volunteerId, int eventId) {
        return likeRepository.findByVolunteerIdAndEventId(volunteerId, eventId);
    }

    // 根據活動ID計算收藏的數量
    public int countLikesByEventId(int eventId) {
        return likeRepository.countByEventId(eventId);
    }
}
