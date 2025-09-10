package com.example.LMS.service.impl;

import java.time.Instant;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.Submission;
import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.SubmissionDTO;
import com.example.LMS.repository.AssignmentRepository;
import com.example.LMS.repository.SubmissionRepository;
import com.example.LMS.repository.UserRepository;
import com.example.LMS.service.SubmissionService;
import com.example.LMS.utils.constant.SubmissionEnum;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public SubmissionServiceImpl(AssignmentRepository assignmentRepository,
            SubmissionRepository submissionRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Submission create(long assignmentId, long userId, String fileUrl) {
        Assignment assignment = this.assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AlreadyExistsException("user không tồn tại"));

        Submission res = new Submission();
        res.setSubmitDate(Instant.now());
        res.setStatus(SubmissionEnum.SUBMITTED);
        res.setFileUrl(fileUrl);

        res.setAssignment(assignment);
        res.setStudent(user);
        return this.submissionRepository.save(res);
    }

    @Override
    public Submission update(long SubmissionId, long userId, String fileUrl) {
        Submission res = this.submissionRepository.findById(SubmissionId)
                .orElseThrow(() -> new AlreadyExistsException("submission không tồn tại"));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AlreadyExistsException("user không tồn tại"));
        if (!res.getStudent().getEmail().equals(user.getEmail())) {
            throw new AlreadyExistsException("Bạn không có quyền update submission này");
        }
        res.setSubmitDate(Instant.now());
        res.setStatus(SubmissionEnum.SUBMITTED);
        res.setFileUrl(fileUrl);

        return this.submissionRepository.save(res);
    }

    @Override
    public void delete(long assignmentId, long submissionId, long userId) {
        Submission res = this.submissionRepository.findById(submissionId)
                .orElseThrow(() -> new AlreadyExistsException("submission không tồn tại"));
        Assignment assignment = this.assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AlreadyExistsException("user không tồn tại"));

        if (!res.getAssignment().equals(assignment)) {
            throw new AlreadyExistsException("Submission không thuộc assignment này");
        }
        if (!res.getStudent().getEmail().equals(user.getEmail())) {
            throw new AlreadyExistsException("Bạn không có quyền xóa submission này");
        }

        this.submissionRepository.delete(res);
    }

    @Override
    public Submission getSubmissionById(long id) {
        return this.submissionRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("submission không tồn tại"));
    }

    @Override
    public SubmissionDTO convertSubmissionDTO(Submission submission) {
        SubmissionDTO res = new SubmissionDTO();
        res.setId(submission.getId());
        res.setStatus(submission.getStatus());
        res.setSubmitDate(submission.getSubmitDate());
        res.setUser(submission.getStudent().getName());
        return res;
    }

}
