package com.example.LMS.domain.dto;

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
    private String instructor;
    private String description;
    private boolean active;
    private String category;
}