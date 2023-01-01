package com.ramij.CacheInvalidator.service.impl;

import com.ramij.CacheInvalidator.Tools;
import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.persistence.StudentRepo;
import com.ramij.CacheInvalidator.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepo repo;

    @Override
    public Student createStudent(Student s) {
        return repo.save(s);
    }

    @Override
    public Student updateStudent(Long id, Student s) {
        Student updatedStudent=repo.getReferenceById(id);
        updatedStudent.setCourse(Tools.getOrDefault(s.getCourse(),updatedStudent.getCourse()));
        updatedStudent.setEmail(Tools.getOrDefault(s.getEmail(),updatedStudent.getEmail()));
        updatedStudent.setName(Tools.getOrDefault(s.getName(),updatedStudent.getName()));
        return repo.save(updatedStudent);
    }

    @Override
    public Student deleteStudent(Long id) {
        Student deletedStudent=repo.getReferenceById(id);
        //ToDo: implements repo.findBy()
        repo.deleteById(id);
        return deletedStudent;
    }

    @Override
    public Student getStudentById(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public Student getStudentByRegNo(String regNo) {
        return repo.getStudentByRegNo(regNo);
    }

    @Override
    public List<Student> getAll() {
        return repo.findAll();
    }
}
