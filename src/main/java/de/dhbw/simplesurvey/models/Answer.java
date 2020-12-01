package de.dhbw.simplesurvey.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "questionid", referencedColumnName = "id", nullable = false)
	private Question question;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
	private User user;

	public Answer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Answer(String text, Question question, User user) {
		this.text = text;
		this.question = question;
		this.user = user;
	}

}
