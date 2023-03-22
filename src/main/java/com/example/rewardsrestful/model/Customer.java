package com.example.rewardsrestful.model;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.*;

public class Customer {

    private Long id;
    @NotBlank
    private String name;

    @Email
    private String email;
    @Min(0)
    private Integer points;

    public Customer() {
    }

    public Customer(Long id, String name, String email, Integer points) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.points = points;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPoints() {
        return points;
    }
}
