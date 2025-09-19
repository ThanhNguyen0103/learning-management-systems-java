package com.example.LMS.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.Permission;
import com.example.LMS.domain.Role;
import com.example.LMS.domain.User;
import com.example.LMS.repository.PermissionRepository;
import com.example.LMS.repository.RoleRepository;
import com.example.LMS.repository.UserRepository;
import com.example.LMS.utils.constant.GenderEnum;
import com.example.LMS.utils.constant.RoleEnum;

@Service
public class DatabaseInitializer implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();
        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(new Permission("Create a course", "/api/v1/courses", "POST", "COURSES"));
            arr.add(new Permission("Update a course", "/api/v1/courses", "PUT", "COURSES"));
            arr.add(new Permission("Delete a course", "/api/v1/courses/{id}", "DELETE", "COURSES"));
            arr.add(new Permission("Get a course by id", "/api/v1/courses/{id}", "GET", "COURSES"));
            arr.add(new Permission("Get courses with pagination", "/api/v1/courses", "GET", "COURSES"));

            arr.add(new Permission("Create a assignment", "/api/v1/assignments", "POST", "ASSIGNMENTS"));
            arr.add(new Permission("Update a assignment", "/api/v1/assignments", "PUT", "ASSIGNMENTS"));
            arr.add(new Permission("Delete a assignment", "/api/v1/assignments/{id}", "DELETE", "ASSIGNMENTS"));
            arr.add(new Permission("Get a assignment by id", "/api/v1/assignments/{id}", "GET", "ASSIGNMENTS"));
            arr.add(new Permission("Get assignments with pagination", "/api/v1/assignments", "GET", "ASSIGNMENTS"));

            arr.add(new Permission("Create a permission", "/api/v1/permissions", "POST", "PERMISSIONS"));
            arr.add(new Permission("Update a permission", "/api/v1/permissions", "PUT", "PERMISSIONS"));
            arr.add(new Permission("Delete a permission", "/api/v1/permissions/{id}", "DELETE", "PERMISSIONS"));
            arr.add(new Permission("Get a permission by id", "/api/v1/permissions/{id}", "GET", "PERMISSIONS"));
            arr.add(new Permission("Get permissions with pagination", "/api/v1/permissions", "GET", "PERMISSIONS"));

            arr.add(new Permission("Create a Category course", "/api/v1/course-category", "POST", "CATEGORIES COURSE"));
            arr.add(new Permission("Update a Category course", "/api/v1/course-category", "PUT", "CATEGORIES COURSE"));
            arr.add(new Permission("Delete a Category course", "/api/v1/course-category/{id}", "DELETE",
                    "CATEGORIES COURSE"));
            arr.add(new Permission("Get a Category course by id", "/api/v1/course-category/{id}", "GET",
                    "CATEGORIES COURSE"));
            arr.add(new Permission("Get Categories course with pagination", "/api/v1/course-category", "GET",
                    "CATEGORIES COURSE"));

            arr.add(new Permission("Create a role", "/api/v1/roles", "POST", "ROLES"));
            arr.add(new Permission("Update a role", "/api/v1/roles", "PUT", "ROLES"));
            arr.add(new Permission("Delete a role", "/api/v1/roles/{id}", "DELETE", "ROLES"));
            arr.add(new Permission("Get a role by id", "/api/v1/roles/{id}", "GET", "ROLES"));
            arr.add(new Permission("Get roles with pagination", "/api/v1/roles", "GET", "ROLES"));

            arr.add(new Permission("Create a user", "/api/v1/users", "POST", "USERS"));
            arr.add(new Permission("Update a user", "/api/v1/users", "PUT", "USERS"));
            arr.add(new Permission("Delete a user", "/api/v1/users/{id}", "DELETE", "USERS"));
            arr.add(new Permission("Get a user by id", "/api/v1/users/{id}", "GET", "USERS"));
            arr.add(new Permission("Get users with pagination", "/api/v1/users", "GET", "USERS"));

            arr.add(new Permission("Create a enrollment", "/api/v1/enrollments", "POST", "ENROLLMENTS"));
            arr.add(new Permission("Update a enrollment", "/api/v1/enrollments", "PUT", "ENROLLMENTS"));
            arr.add(new Permission("Delete a enrollment", "/api/v1/enrollments/{id}", "DELETE", "ENROLLMENTS"));
            arr.add(new Permission("Get a enrollment by id", "/api/v1/enrollments/{id}", "GET", "ENROLLMENTS"));
            arr.add(new Permission("Get enrollments with pagination", "/api/v1/enrollments", "GET", "ENROLLMENTS"));

            arr.add(new Permission("Create a submission", "/api/v1/submissions", "POST", "SUBMISSIONS"));
            arr.add(new Permission("Update a submission", "/api/v1/submissions", "PUT", "SUBMISSIONS"));
            arr.add(new Permission("Delete a submission", "/api/v1/submissions/{id}", "DELETE", "SUBMISSIONS"));
            arr.add(new Permission("Get a submission by id", "/api/v1/submissions/{id}", "GET", "SUBMISSIONS"));
            arr.add(new Permission("Get submissions with pagination", "/api/v1/submissions", "GET", "SUBMISSIONS"));

            arr.add(new Permission("Download a file", "/api/v1/files", "POST", "FILES"));
            arr.add(new Permission("Upload a file", "/api/v1/files", "GET", "FILES"));

            this.permissionRepository.saveAll(arr);
        }
        if (countRoles == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setName(RoleEnum.ADMIN);
            adminRole.setDescription("Admin thì full permissions");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);
        }
        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("hn");
            adminUser.setAge(25);
            adminUser.setGender(GenderEnum.MALE);
            adminUser.setName("I'm super admin");
            adminUser.setActive(true);
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            Role adminRole = this.roleRepository.findByName(RoleEnum.ADMIN);
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);
        }

        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }

}
