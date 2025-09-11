package com.example.LMS.service;

import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.dto.EnrollRequestDTO;

public interface EnrollmentService {

    Enrollment create(EnrollRequestDTO req);

    void delete(long id);

}
