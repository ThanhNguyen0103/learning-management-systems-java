package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LMS.domain.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
