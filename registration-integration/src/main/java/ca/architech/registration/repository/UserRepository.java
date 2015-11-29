package ca.architech.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ca.architech.registration.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public User findByUsername(@Param("username") String username);
}
