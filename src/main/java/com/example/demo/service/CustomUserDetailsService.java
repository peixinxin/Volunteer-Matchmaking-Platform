package com.example.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.SupervisorRepository;
import com.example.demo.repository.VolunteerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 先查找 volunteer
        Optional<UserDetails> volunteer = volunteerRepository.findByEmail(email)
                .map(vol -> new CustomUserDetails<>(vol));

        if (volunteer.isPresent()) {
            return volunteer.get();
        }

        // 再查找 supervisor
        Optional<UserDetails> supervisor = supervisorRepository.findByEmail(email)
                .map(sup -> new CustomUserDetails<>(sup));

        if (supervisor.isPresent()) {
            return supervisor.get();
        }

        // 最后查找 organization
        Optional<UserDetails> organization = organizationRepository.findByEmail(email)
                .map(org -> new CustomUserDetails<>(org));

        if (organization.isPresent()) {
            return organization.get();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}