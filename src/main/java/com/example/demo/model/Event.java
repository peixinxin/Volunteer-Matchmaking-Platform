package com.example.demo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(name = "supervise_status", nullable = false)
    private String superviseStatus;

    @Column(nullable = false)
    private String category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer click;

    @Column(nullable = false)
    private Integer cost; // 

    @Column(nullable = true)
    private String remark;

    @Column(nullable = false)
    private Integer host; // 新增主持人(host)字段

    @Column(name="review_reason", nullable= false)
    private String reviewReason;

    // Constructors
    public Event() {
        // 设置默认值
        this.superviseStatus = "未審核";
        this.cost = 0;
        this.remark = null;
        //this.startDate = LocalDate.parse("2024-06-15", DateTimeFormatter.ISO_LOCAL_DATE);
        //this.endDate = LocalDate.parse("2024-06-15", DateTimeFormatter.ISO_LOCAL_DATE);
        this.click = 0;
        reviewReason="none";
    }

    public Event(String name, String description, String location, String category, LocalDate startDate, LocalDate endDate, Integer click, Integer cost, String remark, Integer host) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.superviseStatus = "未審核"; // 默认值
        this.category = category;

        this.startDate = startDate;
        this.endDate = endDate;
        //this.startDate = LocalDate.parse("2024-06-15", DateTimeFormatter.ISO_LOCAL_DATE);
        //this.endDate = LocalDate.parse("2024-06-15", DateTimeFormatter.ISO_LOCAL_DATE);
        this.click = 0;
        this.cost = 0;
        this.remark = null;
        this.host = host;
        reviewReason="none";
    }

    // Getters and Setters
    public Long getId() {
        return eventId;
    }

    public void setId(Long id) {
        this.eventId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSuperviseStatus() {
        return superviseStatus;
    }

    public void setSuperviseStatus(String superviseStatus) {
        this.superviseStatus = superviseStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getHost() {
        return host;
    }

    public void setHost(Integer host) {
        this.host = host;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
    
    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReviewReason(){
        return reviewReason;
    }

    public void setReviewReason(String reason){
        this.reviewReason=reason;
    }

}

