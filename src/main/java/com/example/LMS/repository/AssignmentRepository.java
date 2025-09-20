package com.example.LMS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LMS.domain.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Page<Assignment> findByInstructorId(long userId, Pageable Pageable);
}
