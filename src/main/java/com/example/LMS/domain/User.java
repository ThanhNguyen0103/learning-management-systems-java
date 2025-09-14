package com.example.LMS.domain;

import java.time.Instant;
import java.util.List;

import com.example.LMS.utils.SecurityUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String name;
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Enrollment> enrollment;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    List<Assignment> assignment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<Submission> submission;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Course> course;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : null;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : null;
    }
}
