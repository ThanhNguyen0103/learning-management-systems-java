package com.example.LMS.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Role;
import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.domain.res.ResUserLoginDTO;
import com.example.LMS.domain.res.ResUserLoginDTO.UserDTO;
import com.example.LMS.repository.UserRepository;
import com.example.LMS.service.UserService;
import com.example.LMS.utils.constant.RoleEnum;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;

    public UserServiceImpl(UserRepository userRepository,
            RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User create(User user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("Email đã tồn tại");
        }
        User res = new User();
        res.setEmail(user.getEmail());
        res.setPassword(user.getPassword());
        res.setName(user.getName());
        if (user.getRole() != null) {
            Role role = this.roleService.getRoleByName(user.getRole().getName());
            res.setRole(role);
        } else {
            Role role = this.roleService.getRoleByName(RoleEnum.USER);
            res.setRole(role);
        }

        return this.userRepository.save(res);

    }

    @Override
    public User update(User user) {
        User res = this.userRepository.findById(user.getId())
                .orElseThrow(() -> new AlreadyExistsException("User không tồn tại"));

        res.setPassword(user.getPassword());
        res.setName(user.getName());
        if (user.getRole() != null) {
            Role role = this.roleService.getRoleByName(user.getRole().getName());
            res.setRole(role);
        } else {
            Role role = this.roleService.getRoleByName(RoleEnum.USER);
            res.setRole(role);
        }
        return this.userRepository.save(res);
    }

    @Override
    public void delete(long id) {
        User res = this.userRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("User không tồn tại"));
        this.userRepository.delete(res);
    }

    @Override
    public User getUserById(long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("User không tồn tại"));
    }

    @Override
    public ResultPaginationDTO getUserWithPagination(Pageable pageable) {
        Page<User> pages = this.userRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setCurrentPage(pages.getNumber() + 1);
        meta.setPageSize(pages.getSize());
        meta.setPages(pages.getTotalPages());
        meta.setTotal(pages.getTotalElements());

        result.setResult(pages.getContent().stream().map((item) -> this.convertResUser(item)).toList());
        result.setMeta(meta);
        return result;
    }

    @Override
    public UserDTO convertResUser(User user) {

        ResUserLoginDTO.UserDTO res = new ResUserLoginDTO.UserDTO();

        List<ResUserLoginDTO.PermissionDTO> permissions = user.getRole().getPermissions().stream()
                .map(p -> new ResUserLoginDTO.PermissionDTO(
                        p.getApiPath(),
                        p.getMethod(),
                        p.getModule()))
                .toList();

        ResUserLoginDTO.RoleUserDTO roleDTO = new ResUserLoginDTO.RoleUserDTO(user.getRole().getName().name(),
                permissions);
        res.setEmail(user.getEmail());
        res.setId(user.getId());
        res.setName(user.getName());
        res.setRole(roleDTO);
        if (user.getCourse() != null) {
            List<CourseSummaryDTO> courseDTOs = user.getCourse()
                    .stream()
                    .map(c -> {
                        CourseSummaryDTO dto = new CourseSummaryDTO();
                        dto.setId(c.getId());
                        dto.setName(c.getName());
                        dto.setPrice(c.getPrice());
                        return dto;
                    })
                    .toList();

            res.setCourses(courseDTOs);
        }

        return res;
    }

    @Override
    public User handleSaveRefreshToken(String refreshToken, User user) {
        user.setRefreshToken(refreshToken);
        return this.userRepository.save(user);
    }

    @Override
    public User getUserByEmailAndRefreshToken(String email, String token) {
        User user = this.userRepository.findByEmailAndRefreshToken(email, token);
        if (user == null) {
            throw new AlreadyExistsException("User không tồn tại");
        }
        return user;
    }
}
