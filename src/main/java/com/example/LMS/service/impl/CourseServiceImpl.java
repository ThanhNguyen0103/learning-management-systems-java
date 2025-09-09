package com.example.LMS.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Course;
import com.example.LMS.domain.CourseCategory;
import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.ResUserDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.repository.CourseCategoryRepository;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.service.CourseService;
import com.example.LMS.service.UserService;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final CourseCategoryRepository courseCategoryRepository;

    public CourseServiceImpl(UserService userService,
            CourseCategoryRepository courseCategoryRepository,
            CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseCategoryRepository = courseCategoryRepository;
    }

    @Override
    public Course create(Course course) {
        if (this.courseRepository.existsByName(course.getName())) {
            throw new AlreadyExistsException("course name đã tồn tại");
        }
        Course res = new Course();
        res.setActive(course.isActive());
        res.setName(course.getName());
        res.setPrice(course.getPrice());
        res.setDescription(course.getDescription());

        User user = this.userService.getUserById(course.getInstructor().getId());
        res.setInstructor(user);

        CourseCategory courseCategory = this.courseCategoryRepository.findById(course.getCategory().getId())
                .orElseThrow(() -> new AlreadyExistsException("course category không tồn tại"));
        res.setCategory(courseCategory);
        return this.courseRepository.save(res);
    }

    @Override
    public Course update(Course course) {
        Course res = this.courseRepository.findById(course.getId())
                .orElseThrow(() -> new AlreadyExistsException("course không tồn tại"));

        boolean existsName = this.courseRepository.existsByName(course.getName());

        if (!res.getName().equals(course.getName()) && existsName) {
            throw new AlreadyExistsException("course name đã tồn tại");
        }

        res.setActive(course.isActive());
        res.setName(course.getName());
        res.setPrice(course.getPrice());
        res.setDescription(course.getDescription());

        CourseCategory courseCategory = this.courseCategoryRepository.findById(course.getCategory().getId())
                .orElseThrow(() -> new AlreadyExistsException("course category không tồn tại"));
        res.setCategory(courseCategory);
        return this.courseRepository.save(res);
    }

    @Override
    public void delete(long id) {
        Course res = this.courseRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Course không tồn tại"));
        res.setActive(false);
        this.courseRepository.save(res);
    }

    @Override
    public Course getCourseById(long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("course không tồn tại"));
    }

    @Override
    public ResultPaginationDTO getCourseWithPagination(Pageable pageable) {
        Page<Course> pages = this.courseRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setCurrentPage(pages.getNumber() + 1);
        meta.setPageSize(pages.getSize());
        meta.setPages(pages.getTotalPages());
        meta.setTotal(pages.getTotalElements());

        result.setResult(pages.getContent().stream()
                .map(item -> this.convertCourseSummaryDTO(item)).toList());
        result.setMeta(meta);
        return result;
    }

    @Override
    public CourseSummaryDTO convertCourseSummaryDTO(Course course) {
        CourseSummaryDTO res = new CourseSummaryDTO();
        res.setId(course.getId());
        res.setName(course.getName());
        res.setPrice(course.getPrice());
        res.setInstructor(course.getInstructor().getName());
        res.setCategory(course.getCategory().getName());
        res.setActive(course.isActive());
        res.setDescription(course.getDescription());
        return res;

    }

}
