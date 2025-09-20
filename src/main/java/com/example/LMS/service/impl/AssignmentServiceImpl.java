package com.example.LMS.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Assignment;
import com.example.LMS.domain.Course;

import com.example.LMS.domain.dto.AssignmentDTO;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.domain.dto.SubmissionDTO;
import com.example.LMS.domain.res.ResUserLoginDTO;
import com.example.LMS.repository.AssignmentRepository;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.service.AssignmentService;

import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class AssignmentServiceImpl implements AssignmentService {

        private final AssignmentRepository assignmentRepository;
        private final CourseRepository courseRepository;

        public AssignmentServiceImpl(AssignmentRepository assignmentRepository,
                        CourseRepository courseRepository) {
                this.assignmentRepository = assignmentRepository;
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
                                .orElseThrow(() -> new AlreadyExistsException("course không tồn tại"));
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

                // update course và instructor

                // Course course =
                // this.courseRepository.findById(assignment.getCourse().getId())
                // .orElseThrow(() -> new AlreadyExistsException("assignment không tồn tại"));
                // res.setCourse(course);
                // res.setInstructor(course.getInstructor());
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
                res.setName(assignment.getName());
                res.setStatus(assignment.getStatus());
                res.setDescription(assignment.getDescription());
                res.setDueDate(assignment.getDueDate());
                res.setId(assignment.getId());
                ResUserLoginDTO.UserDTO user = new ResUserLoginDTO.UserDTO();
                user.setName(assignment.getCourse().getInstructor().getName());
                res.setCourse(
                                new CourseSummaryDTO(assignment.getCourse().getId(), assignment.getCourse().getName(),
                                                assignment.getCourse().getPrice(), user,
                                                assignment.getCourse().getDescription(),
                                                assignment.getCourse().isActive(), null,
                                                assignment.getCourse().getThumnail(), null, assignment.getCreatedAt(),
                                                assignment.getUpdatedAt(), assignment.getCreatedBy(),
                                                assignment.getUpdatedBy()));
                if (assignment.getSubmissions() != null) {
                        res.setSubmissions(assignment.getSubmissions().stream().map(
                                        item -> new SubmissionDTO(item.getId(), item.getSubmitDate(), item.getStatus(),
                                                        item.getStudent().getName(), item.getFileUrl()))
                                        .toList());
                }

                return res;
        }

        @Override
        public ResultPaginationDTO getAssignmentWithPagination(Pageable pageable) {
                Page<Assignment> pages = this.assignmentRepository.findAll(pageable);
                ResultPaginationDTO result = new ResultPaginationDTO();
                ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

                meta.setCurrentPage(pages.getNumber() + 1);
                meta.setPageSize(pages.getSize());
                meta.setPages(pages.getTotalPages());
                meta.setTotal(pages.getTotalElements());

                result.setResult(pages.getContent().stream()
                                .map(item -> this.convertAssignmentDTO(item)).toList());
                result.setMeta(meta);
                return result;
        }

        @Override
        public ResultPaginationDTO getAssignmentByUserWithPagination(long userId, Pageable pageable) {
                Page<Assignment> pages = this.assignmentRepository.findByInstructorId(userId, pageable);
                ResultPaginationDTO result = new ResultPaginationDTO();
                ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

                meta.setCurrentPage(pages.getNumber() + 1);
                meta.setPageSize(pages.getSize());
                meta.setPages(pages.getTotalPages());
                meta.setTotal(pages.getTotalElements());

                result.setResult(pages.getContent().stream()
                                .map(item -> this.convertAssignmentDTO(item)).toList());
                result.setMeta(meta);
                return result;
        }

}
