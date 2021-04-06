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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Survey {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT")
	private String title;

	@NonNull
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@JsonIgnore
	@OneToMany(mappedBy = "survey")
	private Set<Question> questions;

	@NonNull
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
	private User owner;

}
