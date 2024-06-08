package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "supervisor")
public class Supervisor{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int supervisorId;

    @Column(name = "Email",nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "Name", nullable = true, length = 45)
    private String name;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    // Constructors
    public Supervisor() {
    }

    public Supervisor(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getSupervisorId() {
        return supervisorId;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}