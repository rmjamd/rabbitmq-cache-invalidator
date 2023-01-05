package com.ramij.CacheInvalidator.persistence;

import com.ramij.CacheInvalidator.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s WHERE s.regNo=?1")
    Student getStudentByRegNo(String regNo);

}
