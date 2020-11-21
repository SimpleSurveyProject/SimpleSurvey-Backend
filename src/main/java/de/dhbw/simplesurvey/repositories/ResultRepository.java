package de.dhbw.simplesurvey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

}