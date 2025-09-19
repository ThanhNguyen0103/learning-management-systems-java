package com.example.LMS.domain.res;

import java.time.Instant;
import java.util.List;

import com.example.LMS.domain.dto.CourseSummaryDTO;
import com.example.LMS.utils.constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUserLoginDTO {
    private String accessToken;
    private UserDTO user;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private long id;
        private String email;
        private String name;
        private int age;
        private String address;
        private GenderEnum gender;
        private RoleUserDTO role;
        private boolean active;
        private List<CourseSummaryDTO> courses;
        private Instant createdAt;
        private Instant updatedAt;
        private String createdBy;
        private String updatedBy;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUserDTO {
        private String name;
        private List<PermissionDTO> permissions;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PermissionDTO {
        private String apiPath;
        private String method;
        private String module;
    }
}
