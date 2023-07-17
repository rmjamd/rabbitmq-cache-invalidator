package com.ramij.CacheInvalidator.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ramij.CacheInvalidator.Tools;
import com.ramij.CacheInvalidator.model.Student;
import com.ramij.CacheInvalidator.persistence.StudentRepo;
import com.ramij.CacheInvalidator.rmq.MessageBus;
import com.ramij.CacheInvalidator.rmq.MessageTopic;
import com.ramij.CacheInvalidator.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepo repo;
    private LoadingCache<String, String> cache;   //regNo --> emailId
    @Autowired
    MessageBus messageBus;
    MessageTopic<String> topic;

    @PostConstruct
    void init() {
        cache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String regNo) throws Exception {
                log.info("database Calling with Key => {}", regNo);
                return repo.getStudentEmailIdByRegNo(regNo);
            }
        });
        try {
            topic = messageBus.createTopic(this.getClass().getSimpleName(), String.class);
            topic.addListener(cacheKey -> {
                log.info("InValidating Cache Key => {}", cacheKey);
                cache.invalidate(cacheKey);
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't Initialize MessageBus");
        }

    }

    @Override
    public Student createStudent(Student s) {
        String uniqueID = UUID.randomUUID().toString();
        s.setRegNo(uniqueID);
        return repo.save(s);
    }

    @Override
    public Student updateStudent(Long id, Student s) {
        Student updatedStudent = repo.findById(id).orElse(null);
        if (updatedStudent == null)
            return null;
        updatedStudent.setCourse(Tools.getOrDefault(s.getCourse(), updatedStudent.getCourse()));
        updatedStudent.setEmail(Tools.getOrDefault(s.getEmail(), updatedStudent.getEmail()));
        updatedStudent.setName(Tools.getOrDefault(s.getName(), updatedStudent.getName()));
        Student result = repo.save(updatedStudent);
        if (s.getEmail() != null) {
            cache.invalidate(updatedStudent.getRegNo());
        }
        return result;
    }

    @Override
    public Student deleteStudent(Long id) {
        Optional<Student> deletedStudent = repo.findById(id);
        //ToDo: implements repo.findBy()
        if (!deletedStudent.isEmpty()) {
            repo.deleteById(id);
            try {
                log.info("Invalidating cached EmailId for the RegNo => {},", deletedStudent.get().getRegNo());
                topic.publish(deletedStudent.get().getRegNo());
            } catch (Exception e) {
                throw new RuntimeException("Error Occurred In Deleting Student");
            }
            cache.invalidate(deletedStudent.get().getRegNo());

        }
        return deletedStudent.orElse(null);
    }

    @Override
    public Student getStudentById(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public Student getStudentByRegNo(String regNo) {
        Student s = repo.getStudentByRegNo(regNo);
        if (Tools.isNullOrEmpty(s.getRegNo()))
            return null;
        return s;
    }

    @Override
    public String getStudentEmailIdByRegNo(String regNo) {
        try {
            return cache.get(regNo);
        } catch (ExecutionException e) {
            System.out.println("Exception occurred While fetching emailId from cache");
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        return repo.findAll();
    }

}
