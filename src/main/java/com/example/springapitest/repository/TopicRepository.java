package com.example.springapitest.repository;

import com.example.springapitest.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByCourse_Name(String courseName, Pageable pagination);

    @Query("SELECT t FROM Topic t WHERE t.course.name = :course_name")
    List<Topic> findTestByCourse_Name(@Param("course_name") String courseName);
}
