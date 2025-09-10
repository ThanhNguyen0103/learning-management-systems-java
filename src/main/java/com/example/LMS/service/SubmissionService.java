package com.example.LMS.service;

import com.example.LMS.domain.Submission;
import com.example.LMS.domain.dto.SubmissionDTO;

public interface SubmissionService {
    Submission create(long assignmentId, long userId, String fileUrl);

    Submission update(long submissionId, long userId, String fileUrl);

    void delete(long assignmentId, long submissionId, long userId);

    Submission getSubmissionById(long id);

    SubmissionDTO convertSubmissionDTO(Submission submission);

}
