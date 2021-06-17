package com.epam.user.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.user.dto.UserDTO;
import com.epam.user.exception.NoSuchUserException;
import com.epam.user.exception.ZeroUsersException;
import com.epam.user.service.UserService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("user-api/users")
@RestController
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Value("${message}")
	private String message;

	@ApiIgnore
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}

	@ApiOperation(nickname = "Get user details", value = "Get all details of a user by giving user ID")
	@GetMapping("{id}")
	public ResponseEntity<UserDTO> get(@PathVariable int id) throws NoSuchUserException {
		log.info("In method get, for getting user by ID");
		log.debug("Takes User ID as parameter which is path variable");
		return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
	}

	@ApiOperation(nickname = "Get all users", value = "Get all users present and details of each user")
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAll(@PageableDefault(size = 5) Pageable pageable)
			throws ZeroUsersException {
		log.info("In method getAll, for getting all the users");
		log.debug("getAll method takes Pageable interface from request body");
		return new ResponseEntity<>(userService.getAll(pageable), HttpStatus.OK);
	}

	@ApiOperation(nickname = "Add user", value = "Add a new user")
	@PostMapping
	public ResponseEntity<UserDTO> post(@Valid @RequestBody UserDTO userDTO) {
		log.info("In method post, for adding a user");
		log.debug("post method takes UserDTO as parameter from request body");
		return new ResponseEntity<>(userService.add(userDTO), HttpStatus.CREATED);
	}

	@ApiOperation(nickname = "Delete user", value = "Delete a user by users ID")
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable int id) throws NoSuchUserException {
		log.info("In method delete, for deleting a user by ID");
		log.debug("delete method takes User ID as parameter which is path variable");
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(nickname = "Update user details", value = "Update a user details by giving user ID and updated details")
	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody UserDTO userDTO)
			throws NoSuchUserException {
		log.info("In method update, for updating a user details");
		log.debug("update method takes UserDTO as parameter from request body and User ID which is path variable");
		userDTO.setId(id);
		return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
	}

}
