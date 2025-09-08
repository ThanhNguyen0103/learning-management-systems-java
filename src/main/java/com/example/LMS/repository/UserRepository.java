package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LMS.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
