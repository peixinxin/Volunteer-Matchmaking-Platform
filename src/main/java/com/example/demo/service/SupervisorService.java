package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Supervisor;
import com.example.demo.repository.SupervisorRepository;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    // 獲取所有平台管理員
    public List<Supervisor> getSupervisors() {
        return supervisorRepository.findAll();
    }

    // 根據ID獲取平台管理員
    public Optional<Supervisor> getSupervisorById(Integer id) {
        return supervisorRepository.findById(id);
    }

    // 創建新的平台管理員
    public Supervisor createSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    // 更新平台管理員信息
    public Supervisor updateSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    // 刪除平台管理員
    public void deleteSupervisor(Integer id) {
        supervisorRepository.deleteById(id);
    }

    public Optional<Supervisor> getSupervisorByEmail(String email) {
        return supervisorRepository.findByEmail(email);
    }
}
