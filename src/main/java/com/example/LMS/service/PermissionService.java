package com.example.LMS.service;

import org.springframework.data.domain.Pageable;
import com.example.LMS.domain.Permission;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface PermissionService {
    Permission create(Permission permission);

    Permission update(Permission permission);

    void delete(long id);

    Permission getRoleById(long id);

    ResultPaginationDTO getPermissionWithPagination(Pageable pageable);
}
