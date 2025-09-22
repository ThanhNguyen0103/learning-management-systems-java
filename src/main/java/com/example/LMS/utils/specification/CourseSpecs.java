package com.example.LMS.utils.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.LMS.domain.Course;
import com.example.LMS.domain.Course_;

import jakarta.persistence.criteria.Join;

public class CourseSpecs {
    public static Specification<Course> hasCategories(List<String> categories) {
        return (root, query, builder) -> {
            if (categories == null || categories.isEmpty())
                return null;
            Join<Course, String> joinCategories = root.join(Course_.CATEGORIES); // join collection
            return joinCategories.get("name").in(categories); // bây giờ in() hợp lệ
        };
    }

    public static Specification<Course> keywordSearch(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.isEmpty())
                return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return builder.like(builder.lower(root.get(Course_.NAME)), pattern);
        };
    }

}
