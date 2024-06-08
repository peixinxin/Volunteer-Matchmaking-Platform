package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findAll();
    
    // 根據名稱關鍵字查找組織
    List<Organization> findByNameContaining(String keyword);

    //根據email
    Optional<Organization> findByEmail(String email);
    
}
