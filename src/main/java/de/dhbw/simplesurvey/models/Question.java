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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@JsonIgnore
	@OneToMany(mappedBy = "question")
	private Set<Answer> answers;

	@NonNull
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "surveyid", referencedColumnName = "id", nullable = false)
	private Survey survey;

}
