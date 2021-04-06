package de.dhbw.simplesurvey.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Question;
import de.dhbw.simplesurvey.models.Survey;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	List<Question> findBySurvey(Survey survey);

	Optional<Question> findById(int id);
}