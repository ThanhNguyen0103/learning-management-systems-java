package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.ResUserDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(long id);

    User getUserById(long id);

    User getUserByEmail(String email);

    ResUserDTO convertResUser(User user);

    ResultPaginationDTO getUserWithPagination(Pageable pageable);
}
