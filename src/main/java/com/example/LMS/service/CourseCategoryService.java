package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.CourseCategory;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface CourseCategoryService {
    CourseCategory create(CourseCategory courseCategory);

    CourseCategory update(CourseCategory courseCategory);

    void delete(long id);

    CourseCategory getCourseCategoryById(long id);

    ResultPaginationDTO getCourseCategoryWithPagination(Pageable pageable);
}
