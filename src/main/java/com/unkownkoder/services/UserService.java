package com.unkownkoder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("In the user details service");

		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
	}

	public List<ApplicationUser> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<ApplicationUser> getUserById(Integer userId) {
		return userRepository.findById(userId);
	}

	public ApplicationUser createUser(ApplicationUser user) {
		return userRepository.save(user);
	}

	public ApplicationUser updateUser(Integer userId, ApplicationUser user) {
		Optional<ApplicationUser> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			ApplicationUser current = existingUser.get();

			// Update the existing user with the values from the new user if they are not
			// null or empty
			if (user.getUsername() != null && !user.getUsername().isEmpty()) {
				current.setUsername(user.getUsername());
			}
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				current.setPassword(user.getPassword());
				current.setPassword(encoder.encode(user.getPassword()));

			}
			if (user.getBirthDate() != null) {
				current.setBirthDate(user.getBirthDate());
			}
			if (user.getCountry() != null && !user.getCountry().isEmpty()) {
				current.setCountry(user.getCountry());
			}
			if (user.getWeight() != 0.0) {
				current.setWeight(user.getWeight());
			}

			return userRepository.save(current);
		}
		return null;
	}

	public void deleteUser(Integer userId) {
		userRepository.deleteById(userId);
	}

	public ApplicationUser findByUsername(String username) {
		Optional<ApplicationUser> existingUser = userRepository.findByUsername(username);
		if (existingUser.isPresent()) {
			ApplicationUser user = existingUser.get();
			return user;
		} else
			return null;
	}

}
