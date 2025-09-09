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

import com.example.LMS.domain.Permission;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.service.PermissionService;
import com.example.LMS.utils.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("create permission success")
    public ResponseEntity<Permission> postMethodName(@RequestBody Permission permission) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("update permission success")
    public ResponseEntity<Permission> putMethodName(@RequestBody Permission permission) {
        return ResponseEntity.status(HttpStatus.OK).body(this.permissionService.update(permission));
    }

    @GetMapping("/permissions")
    @ApiMessage("get permissions success")
    public ResponseEntity<ResultPaginationDTO> fetchAllPermissions(Pageable pageable) {
        ResultPaginationDTO permissions = this.permissionService.getPermissionWithPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(permissions);
    }

    @GetMapping("/permissions/{id}")
    @ApiMessage("get permission success")
    public ResponseEntity<Permission> fetchPermissionByID(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.permissionService.getPermissionById(id));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("delete permission success")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") long id) {
        this.permissionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
