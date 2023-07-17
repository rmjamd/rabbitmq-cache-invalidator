
package com.ramij.CacheInvalidator.controller;

import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class UserController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hi! Welcome to this project";
    }

    @GetMapping("/students/regNo/{regNo}")
    public Student getStudentByRegNo(@PathVariable String regNo) {
        log.info("Inside getStudentByRegNo | regNo => {}", regNo);
        return studentService.getStudentByRegNo(regNo);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        log.info("Inside updateStudent | Id => {} | Student Details => {}", id, studentDetails);
        return studentService.updateStudent(id, studentDetails);
    }

    @DeleteMapping("/students/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        log.info("Inside deleteStudent | Id => {}", id);
        return studentService.deleteStudent(id);
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student studentDetails) {
        log.info("Inside createStudent | Student Details => {}", studentDetails);
        return studentService.createStudent(studentDetails);
    }

    @GetMapping("/students/id/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/students/email")
    public String getStudentEmailIdByRegNo(@RequestParam String regNo) {
        return studentService.getStudentEmailIdByRegNo(regNo);
    }

}
