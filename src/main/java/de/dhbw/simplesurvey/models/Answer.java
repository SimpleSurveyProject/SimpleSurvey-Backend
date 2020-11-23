package de.dhbw.simplesurvey.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@ManyToOne
	@JoinColumn(name = "questionid", referencedColumnName = "id", nullable = false)
	private Question question;

	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
	private User user;

}
