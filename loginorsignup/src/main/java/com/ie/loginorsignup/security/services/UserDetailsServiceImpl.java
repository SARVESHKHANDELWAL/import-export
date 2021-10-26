package com.ie.loginorsignup.security.services;

import com.ie.loginorsignup.models.User;
import com.ie.loginorsignup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String companyName) throws UsernameNotFoundException {
		User user = userRepository.findByCompanyName(companyName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + companyName));

		return UserDetailsImpl.build(user);
	}

}
