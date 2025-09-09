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

import com.example.LMS.domain.Role;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.service.RoleService;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiMessage("Create role success")
    public ResponseEntity<Role> postMethodName(@RequestBody Role role) {
        Role res = this.roleService.create(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/roles")
    @ApiMessage("Update role success")
    public ResponseEntity<Role> putMethodName(@RequestBody Role role) {
        Role res = this.roleService.update(role);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete user success")
    public ResponseEntity<Void> deleteMethod(@PathVariable("id") long id) {
        this.roleService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/roles/{id}")
    @ApiMessage("Get role success")
    public ResponseEntity<Role> getMethodName(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.getRoleById(id));
    }

    @GetMapping("/roles")
    @ApiMessage("Get roles with pagination success")
    public ResponseEntity<ResultPaginationDTO> getAllRoleMethod(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.getRoleWithPagination(pageable));
    }
}
