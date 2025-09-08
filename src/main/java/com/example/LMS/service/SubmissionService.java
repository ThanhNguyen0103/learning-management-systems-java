package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.Submission;
import com.example.LMS.domain.res.ResultPaginationDTO;

public interface SubmissionService {
    Submission create(Submission submission);

    Submission update(Submission submission);

    void delete(long id);

    Submission getSubmissionById(long id);

    ResultPaginationDTO getSubmissionWithPagination(Pageable pageable);
}
