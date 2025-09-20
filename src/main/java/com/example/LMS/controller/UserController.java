package com.example.LMS.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.domain.res.ResUserLoginDTO.UserDTO;
import com.example.LMS.service.AssignmentService;
import com.example.LMS.service.CourseService;
import com.example.LMS.service.UserService;
import com.example.LMS.utils.SecurityUtils;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;
    private final AssignmentService assignmentService;

    public UserController(UserService userService,
            PasswordEncoder passwordEncoder, CourseService courseService,
            AssignmentService assignmentService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
    }

    @PostMapping("/users")
    @ApiMessage("Create User success")
    public ResponseEntity<UserDTO> postCreateUser(@RequestBody User user) {
        String pw = passwordEncoder.encode(user.getPassword());
        user.setPassword(pw);
        User res = this.userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertResUser(res));

    }

    @PutMapping("/users")
    @ApiMessage("Update user success")
    public ResponseEntity<UserDTO> putMethodName(@RequestBody User user) {
        User res = this.userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertResUser(res));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete user success")
    public ResponseEntity<Void> deleteMethod(@PathVariable("id") long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Get user success")
    public ResponseEntity<UserDTO> getMethodName(@PathVariable("id") long id) {
        User res = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertResUser(res));
    }

    @GetMapping("/users")
    @ApiMessage("Get user with pagination success")
    public ResponseEntity<ResultPaginationDTO> getAllUserMethod(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserWithPagination(pageable));
    }

    @GetMapping("/users/course")
    @ApiMessage("Get course by user with pagination success")
    public ResponseEntity<ResultPaginationDTO> getCourseByUser(Pageable pageable) {
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        User currentUser = this.userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.courseService.getCourseByUserWithPagination(currentUser.getId(), pageable));
    }

    @GetMapping("/users/assignment")
    @ApiMessage("Get assignment by user with pagination success")
    public ResponseEntity<ResultPaginationDTO> getAssigmnetByUser(Pageable pageable) {
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        User currentUser = this.userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.assignmentService.getAssignmentByUserWithPagination(currentUser.getId(), pageable));
    }

}
