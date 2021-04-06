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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@NonNull
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "questionid", referencedColumnName = "id", nullable = false)
	private Question question;

	@NonNull
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
	private User user;

}
