package com.example.LMS.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.LMS.domain.CourseCategory;
import com.example.LMS.domain.dto.ResultPaginationDTO;
import com.example.LMS.repository.CourseCategoryRepository;
import com.example.LMS.service.CourseCategoryService;
import com.example.LMS.utils.error.AlreadyExistsException;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    private final CourseCategoryRepository courseCategoryRepository;

    public CourseCategoryServiceImpl(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }

    @Override
    public CourseCategory create(CourseCategory courseCategory) {
        if (this.courseCategoryRepository.existsByName(courseCategory.getName())) {
            throw new AlreadyExistsException("Course category name đã tồn tại");
        }
        CourseCategory res = new CourseCategory();
        res.setName(courseCategory.getName());
        res.setActive(courseCategory.isActive());
        return this.courseCategoryRepository.save(res);
    }

    @Override
    public CourseCategory update(CourseCategory courseCategory) {
        CourseCategory res = this.courseCategoryRepository.findById(courseCategory.getId())
                .orElseThrow(() -> new AlreadyExistsException("Course category không tồn tại"));
        boolean exist = this.courseCategoryRepository.existsByName(courseCategory.getName());
        if (exist && !res.getName().equals(courseCategory.getName())) {
            throw new AlreadyExistsException("Course category name đã tồn tại");
        }
        res.setName(courseCategory.getName());
        res.setActive(courseCategory.isActive());
        return this.courseCategoryRepository.save(res);
    }

    @Override
    public void delete(long id) {
        CourseCategory res = this.courseCategoryRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Course category không tồn tại"));
        res.setActive(false);
        this.courseCategoryRepository.save(res);
    }

    @Override
    public CourseCategory getCourseCategoryById(long id) {
        return this.courseCategoryRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Course category không tồn tại"));
    }

    @Override
    public ResultPaginationDTO getCourseCategoryWithPagination(Pageable pageable) {
        Page<CourseCategory> pages = this.courseCategoryRepository.findAll(pageable);
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
