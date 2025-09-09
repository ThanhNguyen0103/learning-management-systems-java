package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.Course;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface CourseService {
    Course create(Course course);

    Course update(Course course);

    void delete(long id);

    Course getCourseById(long id);

    CourseSummaryDTO convertCourseSummaryDTO(Course course);

    ResultPaginationDTO getCourseWithPagination(Pageable pageable);
}
