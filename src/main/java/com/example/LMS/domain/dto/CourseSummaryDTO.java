package com.example.LMS.domain.dto;

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
    private CategoryDTO category;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private long id;
        private String name;

    }
}