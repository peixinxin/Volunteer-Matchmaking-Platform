package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationRepository;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // 獲取所有組織
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    // 根據ID查找組織
    public Optional<Organization> getOrganizationById(int id) {
        return organizationRepository.findById(id);
    }
    
    //根據email
    public Optional<Organization> getOrganizationByEmail(String email) {
        return organizationRepository.findByEmail(email);
    }

    // 創建新組織
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }
    
    // 更新志工信息
    public Organization updateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    // 刪除組織
    public void deleteOrganization(int id) {
        organizationRepository.deleteById(id);
    }

}
