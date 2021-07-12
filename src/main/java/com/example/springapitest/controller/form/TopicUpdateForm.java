package com.example.springapitest.controller.form;

import com.example.springapitest.model.Topic;
import com.example.springapitest.repository.TopicRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicUpdateForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String title;

    @NotNull @NotEmpty @Length(min = 10)
    private String message;

    public Topic update(Long id, TopicRepository topicRepository) {
        Topic topic = topicRepository.getOne(id);
        topic.setTitle(this.title);
        topic.setMessage(this.message);
        return topic;
    }

    public Topic update(Topic topic) {
        topic.setTitle(this.title);
        topic.setMessage(this.message);
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
