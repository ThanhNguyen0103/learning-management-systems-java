package com.example.LMS.controller;

import java.io.IOException;
import java.net.URISyntaxException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.LMS.domain.Course;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.service.CourseService;
import com.example.LMS.service.UploadFileService;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    private final CourseService courseService;
    private final UploadFileService uploadFileService;

    public CourseController(CourseService courseService, UploadFileService uploadFileService) {
        this.courseService = courseService;
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/courses")
    @ApiMessage("create courses success")
    public ResponseEntity<CourseSummaryDTO> postMethodName(@RequestBody Course course) {
        Course course2 = this.courseService.create(course);
        CourseSummaryDTO res = this.courseService.convertCourseSummaryDTO(course2);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/courses")
    @ApiMessage("create courses success")
    public ResponseEntity<CourseSummaryDTO> putMethodName(@RequestBody Course course) {
        Course course2 = this.courseService.update(course);
        CourseSummaryDTO res = this.courseService.convertCourseSummaryDTO(course2);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/courses")
    @ApiMessage("get courses success")
    public ResponseEntity<ResultPaginationDTO> getAllCourse(Pageable pageable) {
        ResultPaginationDTO res = this.courseService.getCourseWithPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/courses/{id}")
    @ApiMessage("get courses success")
    public ResponseEntity<CourseSummaryDTO> getCourseByID(@PathVariable("id") long id) {
        CourseSummaryDTO res = this.courseService.convertCourseSummaryDTO(this.courseService.getCourseById(id));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("/courses/{id}")
    @ApiMessage("delete courses success")
    public ResponseEntity<Void> deleteCourseById(@PathVariable("id") long id) {
        this.courseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException {
        String fileName = this.uploadFileService.uploadSubmission(folder, file);
        return fileName;
    }

}
