package de.dhbw.simplesurvey.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public class SurveyDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@NonNull
	String title;
	
	@NonNull
	String description;
	
	@NonNull
	Integer owner;

}
