package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.res.ResultPaginationDTO;

public interface AssignmentService {

    Assignment create(Assignment assignment);

    Assignment update(Assignment assignment);

    void delete(long id);

    Assignment getAssignmentById(long id);

    ResultPaginationDTO getAssignmentWithPagination(Pageable pageable);
}
