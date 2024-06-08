package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("/")
    public String defaultUrl() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "registration";
    }

    @GetMapping("/userhome")
    public String home() {
        return "userhomePage";
    }

    @GetMapping("/orghomepage")
    public String orgHome() {
        return "orgViewAllEvents";
    }

    @GetMapping("/supervisorhome")
    public String supervisorhome() {
        return "superEvent";
    }

    @GetMapping("/eventVolun")
    public String eventVolun() {
        return "eventVolun";
    }

    @GetMapping("/discussSec")
    public String discussSec() {
        return "discussSec";
    }

    @GetMapping("/volunteerProfile")
    public String volunteerProfile() {
        return "volunteerProfile";
    }
    
    @GetMapping("/orgEventDetail")
    public String orgEventDetail() {
        return "orgEventDetail";
    }
    
    @GetMapping("/viewAllEvents")
    public String viewAllEvents() {
        return "orgviewAllEvents";
    }
    
    @GetMapping("/orgEditEvent")
    public String orgEditEvent() {
        return "orgEditEvent";
    }
    
    @GetMapping("/orgVerifyVolunteer")
    public String orgVerifyVolunteer() {
        return "orgVerifyVolunteer";
    }
    
    @GetMapping("/orgAddEvent")
    public String orgAddEvent() {
        return "orgAddEvent";
    }
    
    @GetMapping("/managePayments")
    public String managePayments() {
        return "managePayments";
    }
    @GetMapping("/orgProfile")
    public String orgProfile() {
        return "orgProfile";
    }
    @GetMapping("/volunteerFriends")
    public String volunteerFriends() {
        return "volunteerFriends";
    }
    @GetMapping("/volunteerLikedEvent")
    public String volunteerLikedEvent() {
        return "volunteerLikedEvent";
    }
    @GetMapping("/volunteerMyEvent")
    public String volunteerMyEvent() {
        return "volunteerMyEvent";
    }
    @GetMapping("/volunteerSettings")
    public String volunteerSettings() {
        return "volunteerSettings";
    }
    @GetMapping("/volEventDetail")
    public String volunteerEventDetail() {
        return "volEventDetail";
    }
    @GetMapping("/superProfile")
    public String superProfile() {
        return "superProfile";
    }

    @GetMapping("/superEventVerify")
    public String superEventVerify() {
        return "superEventVerify";
    }

    @GetMapping("/orgVolunteerDetail")
    public String orgVolunteerDetail() {
        return "orgVolunteerDetail";
    }
    
    @GetMapping("/chatpage")
    public String chatpage() {
        return "chatpage";
    }
}

