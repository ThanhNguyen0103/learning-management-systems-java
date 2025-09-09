package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LMS.domain.Role;
import com.example.LMS.utils.constant.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(RoleEnum name);

    Role findByName(RoleEnum name);

}
