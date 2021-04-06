package de.dhbw.simplesurvey.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT")
	private String name;

	@NonNull
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "TEXT")
	private String pass;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Answer> answers;

	@OneToMany(mappedBy = "owner")
	private Set<Survey> surveys;

}
