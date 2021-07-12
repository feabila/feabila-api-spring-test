package com.example.springapitest.controller.dto;

import com.example.springapitest.model.Topic;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicDto {
    public Long id;
    public String title;
    public String message;
    public LocalDateTime dtCreation;

    public TopicDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.dtCreation = topic.getDtCreation();
    }

    public static Page<TopicDto> convert(Page<Topic> topics) {
        return topics.map(TopicDto::new);
    }
}
