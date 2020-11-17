package de.dhbw.simplesurvey.db;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AnswerDB {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	int questionid;
	String text;
	
}
