package com.example.LMS.domain.dto;

import java.time.Instant;

import com.example.LMS.utils.constant.AssignmentEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private long id;
    private String name;
    private String description;
    private Instant assignedDate;
    private Instant dueDate;
    private AssignmentEnum status;
    private String instructor;
    private String course;
}
