package com.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserDetailService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public UserDetailService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByEmail(username);

		return new UserDetails() {

			@Override
			public String getUsername() {
				if (user.getUsername().equals(username)) {
					return user.getUsername();
				} else if (user.getEmail().equals(username)) {
					return user.getEmail();
				}

				throw new EntityNotFoundException();
			}

			@Override
			public String getPassword() {
				return user.getPassword();
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return List.of(new SimpleGrantedAuthority(user.getRoles().toString()));
			}
		};

	}

}
