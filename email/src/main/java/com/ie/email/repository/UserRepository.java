package com.ie.email.repository;

import com.ie.email.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   // @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByEmailOtp(String emailOtp);

    public boolean existsByEmailOtp(String emailOtp);

    public User findByMobileOtp(String mobileOtp);

    public boolean existsByMobileOtp(String mobileOtp);
}
