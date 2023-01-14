package com.ramij.CacheInvalidator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    @MockBean
    StudentService studentService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    public void getStudentById() throws Exception {
        Student s=new Student();
        s.setRegNo("abcd");
        s.setName("ramij");
        s.setCourse("CSE");
        s.setEmail("abc@gmail.com");
        s.setId(1L);
        Mockito.when(studentService.getStudentById(s.getId())).thenReturn(s);
        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/api/student/1"))
                .andReturn();
        result.getResponse();
    }
}
