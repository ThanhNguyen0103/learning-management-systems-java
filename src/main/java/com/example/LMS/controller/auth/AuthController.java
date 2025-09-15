package com.example.LMS.controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.domain.User;
import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.domain.dto.LoginDTO;
import com.example.LMS.domain.res.ResUserLoginDTO;
import com.example.LMS.service.UserService;
import com.example.LMS.utils.SecurityUtils;
import com.example.LMS.utils.annotation.ApiMessage;
import com.example.LMS.utils.constant.TokenType;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
        @Value("${thanh.jwt.refresh-token.validity-in-seconds}")
        private long expiresRefreshToken;
        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final UserService userService;
        private final SecurityUtils securityUtils;

        private final PasswordEncoder passwordEncoder;

        public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
                        UserService userService,
                        PasswordEncoder passwordEncoder,
                        SecurityUtils securityUtils) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.userService = userService;
                this.securityUtils = securityUtils;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/auth/login")
        @ApiMessage("Login success")
        public ResponseEntity<ResUserLoginDTO> postLogin(@RequestBody LoginDTO user) {
                UsernamePasswordAuthenticationToken userToke = new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword());
                Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(userToke);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                User currentUser = this.userService.getUserByEmail(user.getUsername());

                List<ResUserLoginDTO.PermissionDTO> permissions = currentUser.getRole().getPermissions().stream()
                                .map(p -> new ResUserLoginDTO.PermissionDTO(
                                                p.getApiPath(),
                                                p.getMethod(),
                                                p.getModule()))
                                .toList();
                List<CourseSummaryDTO> courseDTOs = currentUser.getCourse()
                                .stream()
                                .map(c -> {
                                        CourseSummaryDTO dto = new CourseSummaryDTO();
                                        dto.setId(c.getId());
                                        dto.setName(c.getName());
                                        dto.setPrice(c.getPrice());
                                        return dto;
                                })
                                .toList();

                ResUserLoginDTO.RoleUserDTO role = new ResUserLoginDTO.RoleUserDTO(
                                currentUser.getRole().getName().name(),
                                permissions);

                ResUserLoginDTO.UserDTO userLogin = new ResUserLoginDTO.UserDTO(currentUser.getId(),
                                currentUser.getEmail(),
                                currentUser.getName(), role, currentUser.isActive(), courseDTOs);

                String accessToken = this.securityUtils.generateJwt(currentUser,
                                TokenType.ACCESS);
                String refreshToken = this.securityUtils.generateJwt(currentUser,
                                TokenType.REFRESH);

                this.userService.handleSaveRefreshToken(refreshToken, currentUser);
                ResUserLoginDTO res = new ResUserLoginDTO(accessToken, userLogin);

                ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(expiresRefreshToken)
                                .build();

                return ResponseEntity
                                .ok()
                                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                                .body(res);
        }

        @GetMapping("/auth/refresh")
        @ApiMessage("refresh success")
        public ResponseEntity<ResUserLoginDTO> getRefresh(
                        @CookieValue(required = false, name = "refreshToken") String refreshToken) {

                String email = this.securityUtils.extractSubjectFromValidRefreshToken(refreshToken);
                User currentUser = this.userService.getUserByEmailAndRefreshToken(email, refreshToken);

                List<ResUserLoginDTO.PermissionDTO> permissions = currentUser.getRole().getPermissions().stream()
                                .map(p -> new ResUserLoginDTO.PermissionDTO(
                                                p.getApiPath(),
                                                p.getMethod(),
                                                p.getModule()))
                                .toList();
                List<CourseSummaryDTO> courseDTOs = currentUser.getCourse()
                                .stream()
                                .map(c -> {
                                        CourseSummaryDTO dto = new CourseSummaryDTO();
                                        dto.setId(c.getId());
                                        dto.setName(c.getName());
                                        dto.setPrice(c.getPrice());
                                        return dto;
                                })
                                .toList();

                ResUserLoginDTO.RoleUserDTO role = new ResUserLoginDTO.RoleUserDTO(
                                currentUser.getRole().getName().name(),
                                permissions);

                ResUserLoginDTO.UserDTO userLogin = new ResUserLoginDTO.UserDTO(currentUser.getId(),
                                currentUser.getEmail(),
                                currentUser.getName(), role, currentUser.isActive(), courseDTOs);

                String accessToken = this.securityUtils.generateJwt(currentUser,
                                TokenType.ACCESS);
                String newRefreshToken = this.securityUtils.generateJwt(currentUser,
                                TokenType.REFRESH);
                this.userService.handleSaveRefreshToken(refreshToken, currentUser);
                ResUserLoginDTO res = new ResUserLoginDTO(accessToken, userLogin);
                ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(expiresRefreshToken)
                                .build();

                return ResponseEntity
                                .ok()
                                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                                .body(res);
        }

        @GetMapping("/auth/account")
        @ApiMessage("get account success")
        public ResponseEntity<ResUserLoginDTO.UserDTO> getAccount() {
                String email = SecurityUtils.getCurrentUserLogin().isPresent()
                                ? SecurityUtils.getCurrentUserLogin().get()
                                : "";
                User currentUser = this.userService.getUserByEmail(email);

                List<ResUserLoginDTO.PermissionDTO> permissions = currentUser.getRole().getPermissions().stream()
                                .map(p -> new ResUserLoginDTO.PermissionDTO(
                                                p.getApiPath(),
                                                p.getMethod(),
                                                p.getModule()))
                                .toList();

                List<CourseSummaryDTO> courseDTOs = currentUser.getCourse()
                                .stream()
                                .map(c -> {
                                        CourseSummaryDTO dto = new CourseSummaryDTO();
                                        dto.setId(c.getId());
                                        dto.setName(c.getName());
                                        dto.setPrice(c.getPrice());
                                        return dto;
                                })
                                .toList();

                ResUserLoginDTO.RoleUserDTO role = new ResUserLoginDTO.RoleUserDTO(
                                currentUser.getRole().getName().name(),
                                permissions);
                ResUserLoginDTO.UserDTO userLogin = new ResUserLoginDTO.UserDTO(currentUser.getId(),
                                currentUser.getEmail(),
                                currentUser.getName(), role, currentUser.isActive(), courseDTOs);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(userLogin);
        }

        @GetMapping("/auth/logout")
        @ApiMessage("Logout success")
        public ResponseEntity<Void> postLogout() {
                String email = SecurityUtils.getCurrentUserLogin() != null
                                ? SecurityUtils.getCurrentUserLogin().get()
                                : "";
                User currentUser = this.userService.getUserByEmail(email);
                this.userService.handleSaveRefreshToken(null, currentUser);
                ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", null)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(0)
                                .build();
                return ResponseEntity
                                .ok()
                                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                                .body(null);
        }

}
