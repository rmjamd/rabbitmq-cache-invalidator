package com.ramij.CacheInvalidator.controller;

import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class UserController {
    @Autowired
    StudentService studentService;
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getGreeting() {
        return "hi! welcome to this Project";
    }
//    @RequestMapping(value = "/student", method = RequestMethod.GET)
//    public Student getTestData() {
//        return Student.builder().id("1234").name("ramij").course("cse").email("ramijnalpur@gmail.com").build();
//    }
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public Student getStudentByRegNo(@RequestParam String regNo) {
        return studentService.getStudentByRegNo(regNo);
    }
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<Student> getAllStudent() {
        return studentService.getAll();
    }
    @RequestMapping(value="/student/{id}" ,method = RequestMethod.PUT)
    public Student updateStudent(@PathVariable Long id,@RequestBody Student studentDetails){
        return studentService.updateStudent(id,studentDetails);
    }
    @RequestMapping(value="/student/{id}",method=RequestMethod.DELETE)
    public Student deleteStudent(@RequestParam Long id){
        return studentService.deleteStudent(id);
    }
    @RequestMapping(value="/student/create",method =RequestMethod.POST)
    public Student createStudent(@RequestBody Student studentDetails){
        return studentService.createStudent(studentDetails);
    }
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


}
