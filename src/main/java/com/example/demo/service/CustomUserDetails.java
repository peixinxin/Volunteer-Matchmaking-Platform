package com.example.demo.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails<T> implements UserDetails {

    //private User user;
    private T user;

    public CustomUserDetails(T user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        if (user instanceof com.example.demo.model.Volunteer) {
            return ((com.example.demo.model.Volunteer) user).getPassword();
        } else if (user instanceof com.example.demo.model.Organization) {
            return ((com.example.demo.model.Organization) user).getPassword();
        } else if (user instanceof com.example.demo.model.Supervisor) {
            return ((com.example.demo.model.Supervisor) user).getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user instanceof com.example.demo.model.Volunteer) {
            return ((com.example.demo.model.Volunteer) user).getEmail();
        } else if (user instanceof com.example.demo.model.Organization) {
            return ((com.example.demo.model.Organization) user).getEmail();
        } else if (user instanceof com.example.demo.model.Supervisor) {
            return ((com.example.demo.model.Supervisor) user).getEmail();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
