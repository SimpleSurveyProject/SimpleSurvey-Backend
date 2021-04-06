package de.dhbw.simplesurvey.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Survey;
import de.dhbw.simplesurvey.models.User;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
	List<Survey> findByOwner(User owner);

	Optional<Survey> findById(int id);
}