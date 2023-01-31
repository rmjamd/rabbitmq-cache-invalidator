package com.ramij.CacheInvalidator.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramij.CacheInvalidator.model.RabbitmqProperty;
import com.ramij.CacheInvalidator.model.Student;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

@SpringBootTest
//@WebMvcTest(UserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void getStudentById() throws Exception {
        String studentJson = "    {\n" +
                "        \"id\": 5,\n" +
                "        \"name\": \"ramij roy\",\n" +
                "        \"email\": \"ramijnalpeerr@gmail.com\",\n" +
                "        \"course\": \"CHM\",\n" +
                "        \"regNo\": \"bc48e415-d448-46cb-98be-96dbc9f5ea89\"\n" +
                "    }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/5"))
                .andReturn();
        Student s = mapFromJson(studentJson,Student.class);
        Student obtainedStudent=mapFromJson(result.getResponse().getContentAsString(),Student.class);
        Assert.assertEquals(s,obtainedStudent);
    }

    @Test
    public void createStudent() throws Exception {
        Student s = new Student();
        s.setName("ramij");
        s.setCourse("CSE");
        s.setEmail("abc@gmail.com");
        String inputJson = mapToJson(s);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                )
                .andReturn();
        String str = result.getResponse().getContentAsString();
        JSONAssert.assertNotEquals(result.getResponse().getContentAsString(), new JSONObject(), false);
    }
    @Test
    public void updateStudents() throws Exception {
        Student s=new Student();
        s.setName("mijanur da");
        s.setCourse("ETC");
        s.setEmail("ramij@gmail.com");
        String inputJson = mapToJson(s);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        //updating some field in student
        s.setEmail("ram@gmail.com");
        s.setCourse("EVE");
        String updatedJson=mapToJson(s);
        Long id=mapFromJson(result.getResponse().getContentAsString().toString(), Student.class).getId();
        MvcResult newResult=mockMvc.perform(MockMvcRequestBuilders.put("/api/student/update/"+id)
                .content(updatedJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Student updatedStudent=mapFromJson(newResult.getResponse().getContentAsString(),Student.class);
        System.out.println(updatedStudent);
        Assert.assertEquals(updatedStudent.getCourse(),"EVE");
    }
    @Test
    public void deleteStudent() throws Exception {
        Student s=new Student().setName("Rohit roy").setCourse("IT").setEmail("abc@gmail.com");
        String inputJson = mapToJson(s);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        Long id=mapFromJson(result.getResponse().getContentAsString().toString(), Student.class).getId();
        MvcResult newResult=mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/delete/"+id)).andReturn();
        Student deletedStudent=mapFromJson(newResult.getResponse().getContentAsString(),Student.class);
        System.out.println(deletedStudent);
        Assert.assertEquals(deletedStudent.getEmail(),s.getEmail());
        Assert.assertEquals(deletedStudent.getCourse(),s.getCourse());

    }

    @Autowired
    RabbitmqProperty rmqProperty;


}
