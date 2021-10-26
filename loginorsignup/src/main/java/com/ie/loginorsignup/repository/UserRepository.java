package com.ie.loginorsignup.repository;

import com.ie.loginorsignup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByCompanyName(String companyName);

	Boolean existsByCompanyName(String companyName);

	Boolean existsByEmail(String email);

	Boolean existsByMobile(String mobile);
}
