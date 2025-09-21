package com.example.LMS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.EnrollRequestDTO;
import com.example.LMS.service.EnrollmentService;
import com.example.LMS.utils.annotation.ApiMessage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollments")
    @ApiMessage("create enrollments success")
    public ResponseEntity<Enrollment> postMethodName(
            @RequestBody EnrollRequestDTO enrollRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enrollmentService.create(enrollRequestDTO));
    }

    @GetMapping("/enrollments/{id}")
    @ApiMessage("delete enrollments success")
    public ResponseEntity<List<CourseSummaryDTO.EnrollmentDTO>> getEnrollByUser(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.enrollmentService.getEnrollByUser(id));
    }

    @DeleteMapping("/enrollments/{id}")
    @ApiMessage("delete enrollments success")
    public ResponseEntity<Void> deleteEnroll(@PathVariable("id") long id) {
        this.enrollmentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
