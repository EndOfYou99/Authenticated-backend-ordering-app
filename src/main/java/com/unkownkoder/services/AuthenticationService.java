package com.unkownkoder.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.models.LoginResponseDTO;
import com.unkownkoder.models.Role;
import com.unkownkoder.repository.RoleRepository;
import com.unkownkoder.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	public ApplicationUser registerUser(ApplicationUser user) {

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();

		authorities.add(userRole);

		ApplicationUser newUser = new ApplicationUser(0, user.getUsername(), encodedPassword, authorities);

		newUser.setWeight(user.getWeight());
		newUser.setCountry(user.getCountry());
		newUser.setBirthDate(user.getBirthDate());
		newUser.setAuthorities(authorities);

		return userRepository.save(newUser);
	}

	public ApplicationUser updateUser(Integer userId, ApplicationUser user) {
		Optional<ApplicationUser> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			user.setUserId(userId);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			return userRepository.save(user);
		}
		return null;
	}

	public LoginResponseDTO loginUser(String username, String password) {

		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			String token = tokenService.generateJwt(auth);

			return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

		} catch (AuthenticationException e) {
			return new LoginResponseDTO(null, "");
		}
	}

}
