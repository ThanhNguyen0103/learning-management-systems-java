package com.example.LMS.domain.dto;

import java.time.Instant;
import java.util.List;

import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.res.ResUserLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummaryDTO {
    private long id;
    private String name;
    private double price;
    private ResUserLoginDTO.UserDTO instructor;
    private String description;
    private boolean active;
    private List<CategoryDTO> categories;
    private String thumnail;
    private List<EnrollmentDTO> enrollments;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private long id;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnrollmentDTO {
        private long id;
        private Instant enrollDate;
        private String name;
        private boolean status;
        private long courseId;

    }
}