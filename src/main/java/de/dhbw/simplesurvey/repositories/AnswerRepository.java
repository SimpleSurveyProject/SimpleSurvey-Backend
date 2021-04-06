package de.dhbw.simplesurvey.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Answer;
import de.dhbw.simplesurvey.models.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	List<Answer> findByQuestion(Question question);
}