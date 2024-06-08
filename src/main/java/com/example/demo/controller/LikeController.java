
package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Like;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.LikeService;


@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private LikeService likeService;

    @GetMapping("/find/{volunteerId}/{eventId}")
    public ResponseEntity<GenericResponse> getLikeByVAndEId(@PathVariable("volunteerId") Integer volunteerId, @PathVariable("eventId") Integer eventId) {
        try {
            Like like = likeService.getLikeByVAndEId(volunteerId, eventId);
            if (like != null) {
                return ResponseEntity.ok(new GenericResponse(true));    //為true代表有找到
            } else {
                return ResponseEntity.ok(new GenericResponse(false));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new GenericResponse(false));   //為false代表沒找到
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Like>> getAllLikes() {
        List<Like> likes = likeRepository.findAll();
        if (!likes.isEmpty()) {
            return ResponseEntity.ok(likes);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
    //新增收藏 
    @PostMapping("/add")
    public ResponseEntity<Like> addLike(
        @RequestBody AddingLikeRequest addingLikeRequest) {
            Like like = new Like();
            like.setVolunteerId(addingLikeRequest.getVolunteerId());
            like.setEventId(addingLikeRequest.getEventId());
            if (likeService.getLikeByVAndEId(like.getVolunteerId(), like.getEventId()) == null) {
                Like newLike = likeRepository.save(like);
                return ResponseEntity.ok(newLike);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }
    //刪除收藏
    @PostMapping("/remove")
    public ResponseEntity<Void> removeLike(
        @RequestBody AddingLikeRequest addingLikeRequest) {
            try {
                Like like = likeService.getLikeByVAndEId(addingLikeRequest.getVolunteerId(), addingLikeRequest.getEventId());
                likeService.deleteLike(like.getLikeId());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }
    //根據志工ID查找所有收藏
    @GetMapping("/{volunteerId}") // 先抓取like
    public ResponseEntity<List<Like>> getLikesById(@PathVariable("volunteerId") Integer volunteerId) {
        List<Like> likes = likeService.getLikesByVolunteerId(volunteerId);
        if (!likes.isEmpty()) {
            return ResponseEntity.ok(likes);
        } else {
            return ResponseEntity.notFound().build();
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
    public static class AddingLikeRequest {
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


}

