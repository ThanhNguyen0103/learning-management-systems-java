package com.example.LMS.service.impl;

import org.springframework.stereotype.Service;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.Course;
import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.AssignmentDTO;
import com.example.LMS.repository.AssignmentRepository;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.service.AssignmentService;
import com.example.LMS.service.UserService;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository,
            UserService userService, CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    @Override
    public Assignment create(Assignment assignment) {
        Assignment res = new Assignment();
        res.setName(assignment.getName());
        res.setDescription(assignment.getDescription());
        res.setAssignedDate(assignment.getAssignedDate());
        res.setDueDate(assignment.getDueDate());
        res.setStatus(assignment.getStatus());

        Course course = this.courseRepository.findById(assignment.getCourse().getId())
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        res.setCourse(course);
        res.setInstructor(course.getInstructor());

        return this.assignmentRepository.save(res);
    }

    @Override
    public Assignment update(Assignment assignment) {
        Assignment res = this.assignmentRepository.findById(assignment.getId())
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        res.setName(assignment.getName());
        res.setDescription(assignment.getDescription());
        res.setAssignedDate(assignment.getAssignedDate());
        res.setDueDate(assignment.getDueDate());
        res.setStatus(assignment.getStatus());

        Course course = this.courseRepository.findById(assignment.getCourse().getId())
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        res.setCourse(course);
        res.setInstructor(course.getInstructor());
        return this.assignmentRepository.save(res);
    }

    @Override
    public void delete(long id) {
        Assignment res = this.assignmentRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
        this.assignmentRepository.delete(res);
    }

    @Override
    public Assignment getAssignmentById(long id) {
        return this.assignmentRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
    }

    @Override
    public AssignmentDTO convertAssignmentDTO(Assignment assignment) {
        AssignmentDTO res = new AssignmentDTO();
        res.setAssignedDate(assignment.getAssignedDate());
        res.setCourse(assignment.getCourse().getName());
        res.setDescription(assignment.getDescription());
        res.setDueDate(assignment.getDueDate());
        res.setId(assignment.getId());
        res.setInstructor(assignment.getInstructor().getName());
        res.setName(assignment.getName());
        res.setStatus(assignment.getStatus());
        return res;
    }

}
