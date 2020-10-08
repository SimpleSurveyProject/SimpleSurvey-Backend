package de.dhbw.simplesurvey.types;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Survey implements Serializable {

	private String title;

}
