package com.ie.loginorsignup.repository;

import com.ie.loginorsignup.models.ERole;
import com.ie.loginorsignup.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
