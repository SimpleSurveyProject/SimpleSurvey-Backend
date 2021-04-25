package de.dhbw.simplesurvey.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(nullable = false)
	private Integer position;

	@NonNull
	@Enumerated(EnumType.STRING)
	private Styles style;

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

	public static enum Styles {
		TEXT, YESNO, ONETOTEN
	}

}
