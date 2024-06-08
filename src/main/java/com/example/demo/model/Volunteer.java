package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "volunteer")
public class Volunteer  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int volunteerId;

    @Column(name = "email",nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "name", nullable = true, length = 45)
    private String name;

    @Column(name = "gender", nullable = true, length = 200)
    private String gender;

    @Column(name = "preference", nullable = true, length = 200)
    private String preference;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "introduction", nullable = true, length = 200)
    private String introduction;

    @Column(name = "age", nullable = true)
    private Integer age;

    // Constructors
    public Volunteer() {
    }
    public Volunteer(String name, String email,String password) {
        this.name=name;
        this.email=email;
        this.password=password;
    }

    public Volunteer(String email, String name, String gender, String preference, String introduction, String password,Integer age) {
        this.name=name;
        this.email=email;
        this.password=password;
        this.gender = gender;
        this.preference = preference;
        this.introduction = introduction;
        this.age = age;
    }
    
    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
