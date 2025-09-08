package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface EnrollmentService {

    Enrollment create(Enrollment enrollment);

    Enrollment update(Enrollment enrollment);

    void delete(long id);

    Enrollment getRoleById(long id);

    ResultPaginationDTO getEnrollmentWithPagination(Pageable pageable);
}
