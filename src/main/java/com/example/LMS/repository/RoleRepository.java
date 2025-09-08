package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LMS.domain.Role;
import com.example.LMS.utils.constant.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(RoleEnum name);

    Role findByName(RoleEnum name);

}
