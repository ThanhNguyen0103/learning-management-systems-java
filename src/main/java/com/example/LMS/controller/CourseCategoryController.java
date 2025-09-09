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

import com.example.LMS.domain.CourseCategory;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.service.CourseCategoryService;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CourseCategoryController {
    private final CourseCategoryService courseCategoryService;

    public CourseCategoryController(CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }

    @PostMapping("/course-category")
    @ApiMessage("create course-category success")
    public ResponseEntity<CourseCategory> postMethodName(@RequestBody CourseCategory courseCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseCategoryService.create(courseCategory));
    }

    @PutMapping("/course-category")
    @ApiMessage("create course-category success")
    public ResponseEntity<CourseCategory> putMethodName(@RequestBody CourseCategory courseCategory) {
        return ResponseEntity.status(HttpStatus.OK).body(this.courseCategoryService.update(courseCategory));
    }

    @GetMapping("/course-category")
    @ApiMessage("get course-category success")
    public ResponseEntity<ResultPaginationDTO> fetchAllPermissions(Pageable pageable) {
        ResultPaginationDTO res = this.courseCategoryService.getCourseCategoryWithPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/course-category/{id}")
    @ApiMessage("get course-category success")
    public ResponseEntity<CourseCategory> fetchPermissionByID(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.courseCategoryService.getCourseCategoryById(id));
    }

    @DeleteMapping("/course-category/{id}")
    @ApiMessage("delete course-category success")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") long id) {
        this.courseCategoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
