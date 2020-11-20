package de.dhbw.simplesurvey.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.simplesurvey.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
}