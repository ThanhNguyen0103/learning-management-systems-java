package com.example.LMS.domain.dto;

import java.time.Instant;

import com.example.LMS.utils.constant.SubmissionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private long id;
    private Instant submitDate;
    private SubmissionEnum status;
    private String user;
    private String url;

}
