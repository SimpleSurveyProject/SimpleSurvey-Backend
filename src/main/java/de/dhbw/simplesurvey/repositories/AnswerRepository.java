package de.dhbw.simplesurvey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}