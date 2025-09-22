package com.example.LMS.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCriteriaDTO {
    private List<String> categories;
    private List<String> tags;
    private String keyword;
}
