package com.example.LMS.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.dto.AssignmentDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.service.AssignmentService;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/assignments")
    @ApiMessage("create assignment success")
    public ResponseEntity<AssignmentDTO> postMethodName(@RequestBody Assignment req) {
        Assignment assignment = this.assignmentService.create(req);
        AssignmentDTO res = this.assignmentService.convertAssignmentDTO(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/assignments")
    @ApiMessage("update assignment success")
    public ResponseEntity<AssignmentDTO> putMethodName(@RequestBody Assignment req) {
        Assignment assignment = this.assignmentService.update(req);
        AssignmentDTO res = this.assignmentService.convertAssignmentDTO(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/assignments/{id}")
    @ApiMessage("get assignment success")
    public ResponseEntity<AssignmentDTO> getAssignmentByID(@PathVariable("id") long id) {
        AssignmentDTO res = this.assignmentService.convertAssignmentDTO(this.assignmentService.getAssignmentById(id));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/assignments/{id}")
    @ApiMessage("delete assignment success")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("id") long id) {
        this.assignmentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/assignments")
    @ApiMessage("get assignmentssuccess")
    public ResponseEntity<ResultPaginationDTO> getAllAssignment(Pageable pageable) {
        ResultPaginationDTO res = this.assignmentService.getAssignmentWithPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
