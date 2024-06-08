package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;
import java.util.Optional;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/api/organizationbyemail")
    public ResponseEntity<?> getOrganizationByEmail(@RequestParam String email) {
        Optional<Organization> organization =  organizationService.getOrganizationByEmail(email);
        if (organization.isPresent()) {
            return ResponseEntity.ok(organization.get());
        } else {
            return ResponseEntity.notFound().build();
        }
                
    }

}