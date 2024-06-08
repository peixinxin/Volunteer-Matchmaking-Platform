package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Organization;
import com.example.demo.model.Supervisor;
import com.example.demo.model.Volunteer;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.SupervisorService;
import com.example.demo.service.VolunteerService;

@RestController
@RequestMapping("/api")
public class RegisterController {
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        String userType = registerRequest.getUserType();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String name = registerRequest.getName();

        if (userType.equals("volunteer")) {
            Optional<Volunteer> existingVolunteer = volunteerService.getVolunteerByEmail(email);
            if (existingVolunteer.isPresent()) {
                return new ResponseEntity<>("There is already a volunteer registered with the provided email.", HttpStatus.CONFLICT);
            }
            Volunteer volunteer = new Volunteer(name,email, passwordEncoder.encode(password));
            volunteerService.createVolunteer(volunteer);
            return new ResponseEntity<>(volunteer, HttpStatus.CREATED);
        } else if (userType.equals("organization")) {
            Optional<Organization> existingOrganization = organizationService.getOrganizationByEmail(email);
            if (existingOrganization.isPresent()) {
                return new ResponseEntity<>("There is already an organization registered with the provided email.", HttpStatus.CONFLICT);
            }
            Organization organization = new Organization(name,email, passwordEncoder.encode(password));
            organizationService.createOrganization(organization);
            return new ResponseEntity<>(organization, HttpStatus.CREATED);
        } else if (userType.equals("supervisor")) {
            Optional<Supervisor> existingSupervisor = supervisorService.getSupervisorByEmail(email);
            if (existingSupervisor.isPresent()) {
                return new ResponseEntity<>("There is already a supervisor registered with the provided email.", HttpStatus.CONFLICT);
            }
            Supervisor supervisor = new Supervisor(name,email, passwordEncoder.encode(password));
            supervisorService.createSupervisor(supervisor);
            return new ResponseEntity<>(supervisor, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type.");
        }

    }

    public static class RegisterRequest {
        private String email;
        private String password;
        private String name;
        private String userType;

        // getters and setters
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUserType() {
            return userType;
        }
        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}