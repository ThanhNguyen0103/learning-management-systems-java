package com.example.LMS.service;

import org.springframework.data.domain.Pageable;

import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.domain.res.ResUserLoginDTO.UserDTO;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(long id);

    User getUserById(long id);

    User getUserByEmail(String email);

    UserDTO convertResUser(User user);

    User handleSaveRefreshToken(String refreshToken, User user);

    User getUserByEmailAndRefreshToken(String email, String token);

    ResultPaginationDTO getUserWithPagination(Pageable pageable);

}
