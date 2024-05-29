package dev.indranil.idb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.indranil.idb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByAccountNumber(String accountNumber);
	
	User findByAccountNumber(String accountNumber);
}
