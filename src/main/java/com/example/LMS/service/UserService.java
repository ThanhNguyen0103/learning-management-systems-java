package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.User;
import com.example.LMS.domain.res.ResultPaginationDTO;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(long id);

    User getUserById(long id);

    ResultPaginationDTO getUserWithPagination(Pageable pageable);
}
