package de.dhbw.simplesurvey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}