package com.example.LMS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.LMS.domain.Submission;
import com.example.LMS.domain.dto.SubmissionDTO;
import com.example.LMS.service.SubmissionService;
import com.example.LMS.service.UploadFileService;
import com.example.LMS.utils.annotation.ApiMessage;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final UploadFileService uploadFileService;

    public SubmissionController(SubmissionService submissionService,
            UploadFileService uploadFileService) {
        this.submissionService = submissionService;
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/assignments/{assignmentId}/submissions")
    @ApiMessage("Create submissions success")
    public ResponseEntity<SubmissionDTO> uploadSubmission(
            @PathVariable("assignmentId") long assignmentId,
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {

        String filePath = this.uploadFileService.uploadSubmission(folder, file);
        Submission submission = this.submissionService.create(assignmentId, 7, filePath);
        SubmissionDTO res = this.submissionService.convertSubmissionDTO(submission);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/assignments/{assignmentId}/submissions/{submissionId}")
    @ApiMessage("Update submissions success")
    public ResponseEntity<SubmissionDTO> updateSubmission(
            @PathVariable("submissionId") long submissionId,
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {

        String filePath = this.uploadFileService.uploadSubmission(folder, file);
        Submission submission = this.submissionService.update(submissionId, 7, filePath);
        SubmissionDTO res = this.submissionService.convertSubmissionDTO(submission);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/assignments/{assignmentId}/submissions/{submissionId}")
    @ApiMessage("Delete submissions success")
    public ResponseEntity<Void> deleteSubmission(
            @PathVariable("assignmentId") long assignmentId,
            @PathVariable("submissionId") long submissionId

    ) {
        this.submissionService.delete(assignmentId, submissionId, 7);
        return ResponseEntity.ok().body(null);
    }

}
