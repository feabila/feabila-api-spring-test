package com.example.springapitest.repository;

import com.example.springapitest.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String courseName);
}
