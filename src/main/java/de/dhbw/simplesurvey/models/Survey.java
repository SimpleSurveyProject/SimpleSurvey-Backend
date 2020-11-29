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
public class Survey {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "survey")
	private Set<Question> questions;

	@ManyToOne
	@JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
	private User owner;

	public Survey(String title, String description, User owner) {
		this.title = title;
		this.description = description;
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}
}
