package com.ramij.CacheInvalidator.service;

import com.ramij.CacheInvalidator.model.Student;

import java.util.List;


public interface StudentService {
    public Student createStudent(Student s);

    public Student updateStudent(Long id,Student studentDetails);

    public Student deleteStudent(Long id);

    public Student getStudentById(Long id);
    public Student getStudentByRegNo(String regNo);
    public String getStudentEMailIdByRegNo(String regNo);

    public List<Student> getAll();
}
