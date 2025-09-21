package com.example.LMS.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.LMS.domain.Course;
import com.example.LMS.domain.Enrollment;
import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.EnrollRequestDTO;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.repository.EnrollmentRepository;
import com.example.LMS.repository.UserRepository;
import com.example.LMS.service.EnrollmentService;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
            UserRepository userRepository, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Enrollment create(EnrollRequestDTO enroll) {
        if (this.enrollmentRepository.existsByUserIdAndCourseId(enroll.getUserId(), enroll.getCourseId())) {
            throw new IllegalArgumentException("bạn đã đăng kí khóa học này rồi");
        }
        User user = this.userRepository.findById(enroll.getUserId())
                .orElseThrow(() -> new AlreadyExistsException("user không tồn tại"));
        Course course = this.courseRepository.findById(enroll.getCourseId())
                .orElseThrow(() -> new AlreadyExistsException("course không tồn tại"));
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setStatus(true);
        enrollment.setEnrollDate(Instant.now());
        return this.enrollmentRepository.save(enrollment);
    }

    @Override
    public void delete(long id) {
        Enrollment enrollment = this.enrollmentRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Enrollment không tồn tại"));

        this.enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<CourseSummaryDTO.EnrollmentDTO> getEnrollByUser(long userId) {
        return this.enrollmentRepository.findByUserId(userId).stream()
                .map(item -> new CourseSummaryDTO.EnrollmentDTO(item.getId(), item.getEnrollDate(), null,
                        item.isStatus(), item.getCourse().getId()))
                .toList();
    }

}
