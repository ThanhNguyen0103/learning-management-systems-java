package com.example.LMS.service;

import com.example.LMS.domain.Role;

public interface RoleService {

    Role create(Role role);

    Role update(Role role);

    void delete(long id);

    Role getRoleById(long id);

}
