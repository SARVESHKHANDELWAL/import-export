package com.bezkoder.springjwt.security.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bezkoder.springjwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

	@Autowired
	private User user;

	private Long id;

	private String companyName;

	private String companyDesignation;

	private String firstName;

	private String lastName;

	private String dateOfBirth;

	private String email;

	private String mobile;

	@JsonIgnore
	private String password;

	private String oneTimePassword;

	private Date otpRequestedTime;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String companyName,String companyDesignation,String firstName,
						   String lastName,String dateOfBirth,
						   String email,String mobile, String password,String oneTimePassword,
			Date otpRequestedTime,Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.companyName = companyName;
		this.companyDesignation=companyDesignation;
		this.firstName=firstName;
		this.lastName=lastName;
		this.dateOfBirth=dateOfBirth;
		this.email=email;
		this.mobile=mobile;
		this.password = password;
		this.oneTimePassword=oneTimePassword;
		this.otpRequestedTime=otpRequestedTime;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getCompanyName(),
				user.getCompanyDesignation(),
				user.getFirstName(),
				user.getLastName(),
				user.getDateOfBirth(),
				user.getEmail(),
				user.getMobile(),
				user.getPassword(),
				user.getOneTimePassword(),
				user.getOtpRequestedTime(),
				authorities);
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public Date getOtpRequestedTime() {
		return otpRequestedTime;
	}

	public boolean isOTPRequired() {
		if (this.getOneTimePassword() == null) {
			return false;
		}

		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

		if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
			// OTP expires
			return false;
		}

		return true;
	}


	@Override
	public String getPassword() {
		if (isOTPRequired()) {
			return getOneTimePassword();
		}
		return password;
	}

	public User getUser() {
		return user;
	}

	public String getMobile() {
		return mobile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return mobile;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
