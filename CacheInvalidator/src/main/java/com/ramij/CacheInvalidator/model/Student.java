package com.ramij.CacheInvalidator.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Student {
    private Long id;
    private String name;
    private String email;
    private String course;
    private String regNo;
}
