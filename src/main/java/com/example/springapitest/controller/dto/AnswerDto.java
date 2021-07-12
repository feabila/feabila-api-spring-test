package com.example.springapitest.controller.dto;

import com.example.springapitest.model.Answer;

import java.time.LocalDateTime;

public class AnswerDto {
	private Long id;
	private String message;
	private LocalDateTime dtCreation;
	private String authorName;

	public AnswerDto(Answer answer) {
		this.id = answer.getId();
		this.message = answer.getMessage();
		this.dtCreation = answer.getDtCreation();
		this.authorName = answer.getAuthor().getName();
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getDtCreation() {
		return dtCreation;
	}

	public String getAuthorName() {
		return authorName;
	}
}
