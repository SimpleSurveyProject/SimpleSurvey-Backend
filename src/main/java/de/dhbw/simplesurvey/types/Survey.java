package de.dhbw.simplesurvey.types;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Survey implements Serializable {

	private UUID uuid;
	private String title;

	private List<Answer> list;
	
}
