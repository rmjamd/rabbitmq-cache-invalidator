package com.ramij.CacheInvalidator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Data
@Entity
//defining class name as Table name
@Table(name = "Students")
@NoArgsConstructor()
@Accessors(chain=true)
//defining class name as Table name
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email")
    private String email;
    @Column(name = "Course")
    private String course;
    @Column(name = "RegNo")
    private String regNo;
}
