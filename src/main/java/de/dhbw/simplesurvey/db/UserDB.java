package de.dhbw.simplesurvey.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public class UserDB {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@NonNull
	String name;
	
	@NonNull
	String pass;

}
