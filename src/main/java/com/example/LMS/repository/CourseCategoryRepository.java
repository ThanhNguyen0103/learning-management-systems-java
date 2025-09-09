package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LMS.domain.CourseCategory;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {
    boolean existsByName(String name);
}
