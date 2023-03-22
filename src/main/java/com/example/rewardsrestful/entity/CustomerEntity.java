package com.example.rewardsrestful.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "points")
    private Integer points;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id, String name, String email, Integer points) {
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
