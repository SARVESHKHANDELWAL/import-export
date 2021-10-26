package com.bezkoder.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByCompanyName(String companyName);

	Optional<User> findByMobile(String mobile);

	Boolean existsByCompanyName(String companyName);

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);
}
