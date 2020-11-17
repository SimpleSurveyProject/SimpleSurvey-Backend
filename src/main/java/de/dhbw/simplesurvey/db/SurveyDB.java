package de.dhbw.simplesurvey.db;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SurveyDB {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String title;
	String description;
	int owner;

}
