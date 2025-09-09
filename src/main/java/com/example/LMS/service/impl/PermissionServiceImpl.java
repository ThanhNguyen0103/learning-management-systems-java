package com.example.LMS.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Permission;

import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.repository.PermissionRepository;
import com.example.LMS.service.PermissionService;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;

    }

    @Override
    public Permission create(Permission permission) {
        if (this.permissionRepository.existsByModuleAndMethodAndApiPath(permission.getModule(), permission.getMethod(),
                permission.getApiPath())) {
            throw new AlreadyExistsException("Permission đã tồn tại");
        }
        Permission res = new Permission();
        res.setApiPath(permission.getApiPath());
        res.setMethod(permission.getMethod());
        res.setModule(permission.getModule());
        res.setName(permission.getName());
        return this.permissionRepository.save(res);

    }

    @Override
    public Permission update(Permission permission) {
        return this.permissionRepository.findById(permission.getId())
                .filter(res -> !this.permissionRepository.existsByModuleAndMethodAndApiPath(
                        permission.getModule(),
                        permission.getMethod(),
                        permission.getApiPath()))
                .map(res -> {
                    res.setApiPath(permission.getApiPath());
                    res.setMethod(permission.getMethod());
                    res.setName(permission.getName());
                    res.setModule(permission.getModule());
                    return this.permissionRepository.save(res);
                })

                .orElseThrow(() -> new AlreadyExistsException("Permission đã tồn tại"));

    }

    @Override
    public void delete(long id) {
        Permission res = this.permissionRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Permission không tồn tại"));
        res.getRoles().stream().forEach(role -> {
            role.getPermissions().remove(res);
        });
        this.permissionRepository.delete(res);
    }

    @Override
    public Permission getPermissionById(long id) {
        return this.permissionRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Permission không tồn tại"));
    }

    @Override
    public ResultPaginationDTO getPermissionWithPagination(Pageable pageable) {
        Page<Permission> pages = this.permissionRepository.findAll(pageable);
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
