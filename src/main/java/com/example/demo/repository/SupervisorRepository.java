package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Supervisor;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
    List<Supervisor> findAll();

    List<Supervisor> findBySupervisorId(int userId);

    List<Supervisor> findByNameContaining(String keyword);

    // findByEmail
    Optional<Supervisor> findByEmail(String email);
}
