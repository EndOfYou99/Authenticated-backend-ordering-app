package com.unkownkoder.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unkownkoder.factory.ApplicationUserFactory;
import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.models.ApplicationUserDTO;
import com.unkownkoder.services.AuthenticationService;
import com.unkownkoder.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET,
		RequestMethod.POST }, allowedHeaders = "*")
public class UserController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationUserFactory userFactory;

	@GetMapping("/")
	public String helloUserController() {
		return "User access level";
	}

	@GetMapping
	public ResponseEntity<List<ApplicationUser>> getAllUsers() {
		List<ApplicationUser> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> getUserById(@PathVariable("id") Integer userId) {
		Optional<ApplicationUser> applicationUser = userService.getUserById(userId);
		if (applicationUser.isPresent()) {
			ApplicationUser user = applicationUser.get();
			ApplicationUserDTO userDto = userFactory.toEntity(user);
			return ResponseEntity.ok(userDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	@PostMapping
//	public ResponseEntity<ApplicationUserDTO> createUser(@RequestBody ApplicationUser user) {
//		authenticationService.registerUser(user.getUsername(), user.getPassword());
//		Integer userId = userService.findByUsername(user.getUsername()).getUserId();
//		ApplicationUser updatedUser = userService.updateUser(userId, user);
//
//		ApplicationUserDTO userDto = userFactory.toEntity(user);
//		return ResponseEntity.ok(userDto);
//	}

	@PutMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> updateUser(@PathVariable("id") Integer userId,
			@RequestBody ApplicationUser user) {
		ApplicationUser updatedUser = userService.updateUser(userId, user);
		if (updatedUser != null) {
			// authenticationService.updateUser(userId, user);
			ApplicationUserDTO userDto = userFactory.toEntity(updatedUser);
			return ResponseEntity.ok(userDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
