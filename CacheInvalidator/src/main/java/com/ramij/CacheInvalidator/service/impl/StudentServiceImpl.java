package com.ramij.CacheInvalidator.service.impl;

import com.ramij.CacheInvalidator.Tools;
import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.persistence.StudentRepo;
import com.ramij.CacheInvalidator.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepo repo;

    @Override
    public Student createStudent(Student s) {
        String uniqueID = UUID.randomUUID().toString();
        s.setRegNo(uniqueID);
        return repo.save(s);
    }

    @Override
    public Student updateStudent(Long id, Student s) {
        Student updatedStudent=repo.findById(id).orElse(null);
        if(updatedStudent==null)
            return updatedStudent;
        updatedStudent.setCourse(Tools.getOrDefault(s.getCourse(),updatedStudent.getCourse()));
        updatedStudent.setEmail(Tools.getOrDefault(s.getEmail(),updatedStudent.getEmail()));
        updatedStudent.setName(Tools.getOrDefault(s.getName(),updatedStudent.getName()));
        return repo.save(updatedStudent);
    }

    @Override
    public Student deleteStudent(Long id) {
        Optional<Student> deletedStudent=repo.findById(id);
        //ToDo: implements repo.findBy()
        if(!deletedStudent.isEmpty()){
            repo.deleteById(id);
        }
        return deletedStudent.orElse(null);
    }

    @Override
    public Student getStudentById(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public Student getStudentByRegNo(String regNo) {
        Student s= repo.getStudentByRegNo(regNo);
        if(Tools.isNullOrEmpty(s.getRegNo()))
            return null;
        return s;
    }

    @Override
    public String getStudentEMailIdByRegNo(String regNo) {
        String name=repo.getStudentEmailIdByRegNo(regNo);
        return name;
    }

    @Override
    public List<Student> getAll() {
        return repo.findAll();
    }
}
