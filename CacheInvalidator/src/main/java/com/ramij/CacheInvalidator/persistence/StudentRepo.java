package com.ramij.CacheInvalidator.persistence;

import com.ramij.CacheInvalidator.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    public default  Student getStudentByRegNo(String regNo){
        return Student.builder().build();
    }

}
