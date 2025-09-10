package com.example.LMS.service;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.dto.AssignmentDTO;

public interface AssignmentService {

    Assignment create(Assignment assignment);

    Assignment update(Assignment assignment);

    void delete(long id);

    Assignment getAssignmentById(long id);

    AssignmentDTO convertAssignmentDTO(Assignment assignment);

}
