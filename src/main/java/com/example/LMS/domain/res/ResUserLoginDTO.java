package com.example.LMS.domain.res;

import java.util.List;

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
        private RoleUserDTO role;
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
