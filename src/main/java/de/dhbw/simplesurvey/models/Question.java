package de.dhbw.simplesurvey.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@OneToMany(mappedBy = "question")
	private Set<Answer> answers;

	@ManyToOne
	@JoinColumn(name = "surveyid", referencedColumnName = "id", nullable = false)
	private Survey survey;

	public Question(String text, Survey survey) {
		this.text = text;
		this.survey = survey;
	}
}
