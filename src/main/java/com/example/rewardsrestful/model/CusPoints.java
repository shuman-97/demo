package com.example.rewardsrestful.model;

public class CusPoints {
    private Long id;
    private String name;
    private String email;
    private Integer points;

    public CusPoints() {
    }

    public CusPoints(Long id, String name, String email, Integer points) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
