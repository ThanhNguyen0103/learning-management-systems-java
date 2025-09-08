package com.example.LMS.service.impl;

import org.springframework.stereotype.Service;

import com.example.LMS.domain.Role;

import com.example.LMS.repository.RoleRepository;
import com.example.LMS.service.RoleService;
import com.example.LMS.utils.constant.RoleEnum;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(RoleEnum name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public Role create(Role role) {

        if (this.roleRepository.existsByName(role.getName())) {
            throw new AlreadyExistsException("Role name đã tồn tại");
        }
        Role res = new Role();
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        res.setActive(role.isActive());
        return this.roleRepository.save(res);
    }

    @Override
    public Role update(Role role) {

        Role currentRole = this.roleRepository.findById(role.getId())
                .orElseThrow(() -> new AlreadyExistsException("Role không tồn tại"));
        boolean exist = this.roleRepository.existsByName(role.getName());
        if (exist && !currentRole.getName().equals(role.getName())) {
            throw new AlreadyExistsException("Role name đã tồn tại");
        }
        currentRole.setActive(role.isActive());
        currentRole.setDescription(role.getDescription());
        currentRole.setName(role.getName());
        return this.roleRepository.save(currentRole);

    }

    @Override
    public void delete(long id) {
        Role role = this.roleRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Role không tồn tại"));
        role.setActive(false);
        this.roleRepository.save(role);
    }

    @Override
    public Role getRoleById(long id) {
        Role role = this.roleRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Role không tồn tại"));
        return role;
    }

}
