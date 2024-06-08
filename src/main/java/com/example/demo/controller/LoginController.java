package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Organization;
import com.example.demo.model.Supervisor;
import com.example.demo.model.Volunteer;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.SupervisorRepository;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.SupervisorService;
import com.example.demo.service.VolunteerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class LoginController {  // 包含登入註冊
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String userType = loginRequest.getUserType();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        switch (userType) {
            case "volunteer":
                return authenticateVolunteer(email, password);
            case "organization":
                return authenticateOrganization(email, password);
            case "supervisor":
                return authenticateSupervisor(email, password);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type.");
        }
    }

    private ResponseEntity<LoginResponse> authenticateVolunteer(String email, String password) {
        Optional<Volunteer> existingVolunteer = volunteerService.getVolunteerByEmail(email);

        if (existingVolunteer.isPresent()) {
            Volunteer volunteer = existingVolunteer.get();
            if (passwordEncoder.matches(password, volunteer.getPassword())) {
                LoginResponse response = new LoginResponse(volunteer.getName(), volunteer.getVolunteerId(), "volunteer", volunteer.getEmail());
                System.out.println("v登入成功 "+ volunteer.getVolunteerId() );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    private ResponseEntity<LoginResponse> authenticateOrganization(String email, String password) {
        Optional<Organization> existingOrganization = organizationService.getOrganizationByEmail(email);

        if (existingOrganization.isPresent()) {
            Organization organization = existingOrganization.get();
            if (passwordEncoder.matches(password, organization.getPassword())) {
                LoginResponse response = new LoginResponse(organization.getName(), organization.getOrganizationId(), "organization", organization.getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    private ResponseEntity<LoginResponse> authenticateSupervisor(String email, String password) {
        Optional<Supervisor> existingSupervisor = supervisorService.getSupervisorByEmail(email);

        if (existingSupervisor.isPresent()) {
            Supervisor supervisor = existingSupervisor.get();
            if (passwordEncoder.matches(password, supervisor.getPassword())) {
                LoginResponse response = new LoginResponse(supervisor.getName(), supervisor.getSupervisorId(), "supervisor", supervisor.getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
    }

    public static class LoginRequest {
        private String email;
        private String password;
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
        public String getUserType() {
            return userType;
        }  
        public void setUserType(String userType) {
            this.userType = userType;
        }
    }

    static class LoginResponse {
        private String name;
        private Integer userId;
        private String userType;
        private String email;

        // constructor, getters and setters
        public LoginResponse(String name, Integer userId, String userType, String email) {
            this.name = name;
            this.userId = userId;
            this.userType = userType;
            this.email = email;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
        public String getUserType() {
            return userType;
        }
        public void setUserType(String userType) {
            this.userType = userType;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
    }
}

