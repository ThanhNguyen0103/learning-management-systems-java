package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.Role;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface RoleService {

    Role create(Role role);

    Role update(Role role);

    void delete(long id);

    Role getRoleById(long id);

    ResultPaginationDTO getRoleWithPagination(Pageable pageable);
}
