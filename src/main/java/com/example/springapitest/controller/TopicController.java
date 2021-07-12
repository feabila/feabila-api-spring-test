package com.example.springapitest.controller;

import com.example.springapitest.controller.dto.TopicDetailsDto;
import com.example.springapitest.controller.dto.TopicDto;
import com.example.springapitest.controller.form.TopicForm;
import com.example.springapitest.controller.form.TopicUpdateForm;
import com.example.springapitest.model.Topic;
import com.example.springapitest.repository.CourseRepository;
import com.example.springapitest.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    @Cacheable(value = "TopicList")
    public Page<TopicDto> list(@RequestParam(required = false) String courseName,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 15) Pageable pagination) {

        Page<Topic> topics;
        if (courseName == null) {
            topics = topicRepository.findAll(pagination);
        } else {
            topics = topicRepository.findByCourse_Name(courseName, pagination);
        }

        return TopicDto.convert(topics);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "TopicList", allEntries = true)
    public ResponseEntity<TopicDto> create(@RequestBody @Valid TopicForm form, UriComponentsBuilder uriBuilder) {
        Topic topic = form.convert(courseRepository);
        topicRepository.save(topic);
        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDto> details(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            return ResponseEntity.ok(new TopicDetailsDto(topic.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "TopicList", allEntries = true)
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateForm form) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            Topic topicUpdated = form.update(topic.get());
            return ResponseEntity.ok(new TopicDto(topicUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "TopicList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
