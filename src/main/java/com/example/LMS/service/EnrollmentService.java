package com.example.LMS.service;

import java.util.List;

import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.EnrollRequestDTO;

public interface EnrollmentService {

    Enrollment create(EnrollRequestDTO req);

    List<CourseSummaryDTO.EnrollmentDTO> getEnrollByUser(long userId);

    void delete(long id);

}
