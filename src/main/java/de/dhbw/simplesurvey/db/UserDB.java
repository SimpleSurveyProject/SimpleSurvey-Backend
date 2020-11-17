package de.dhbw.simplesurvey.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String pass;
	
}
