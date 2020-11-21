package de.dhbw.simplesurvey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
}