package com.example.LMS.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Permission;
import com.example.LMS.domain.Role;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.repository.PermissionRepository;
import com.example.LMS.repository.RoleRepository;
import com.example.LMS.service.RoleService;
import com.example.LMS.utils.constant.RoleEnum;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
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

        List<Permission> permissions = this.permissionRepository.findAllById(
                role.getPermissions().stream().map(p -> p.getId()).toList());
        res.setPermissions(permissions);

        return this.roleRepository.save(res);
    }

    @Override
    public Role update(Role role) {

        Role res = this.roleRepository.findById(role.getId())
                .orElseThrow(() -> new AlreadyExistsException("Role không tồn tại"));
        boolean exist = this.roleRepository.existsByName(role.getName());
        if (exist && !res.getName().equals(role.getName())) {
            throw new AlreadyExistsException("Role name đã tồn tại");
        }
        res.setActive(role.isActive());
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        List<Permission> permissions = this.permissionRepository.findAllById(
                role.getPermissions().stream().map(p -> p.getId()).toList());
        res.setPermissions(permissions);
        return this.roleRepository.save(res);

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
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Role không tồn tại"));
    }

    @Override
    public ResultPaginationDTO getRoleWithPagination(Pageable pageable) {
        Page<Role> pages = this.roleRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setCurrentPage(pages.getNumber() + 1);
        meta.setPageSize(pages.getSize());
        meta.setPages(pages.getTotalPages());
        meta.setTotal(pages.getTotalElements());

        result.setResult(pages.getContent());
        result.setMeta(meta);
        return result;
    }

}
