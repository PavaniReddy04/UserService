package com.epam.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.user.dto.UserDTO;
import com.epam.user.entity.User;
import com.epam.user.exception.NoSuchUserException;
import com.epam.user.exception.ZeroUsersException;
import com.epam.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDTO get(int id) throws NoSuchUserException {
		log.info("In method get, for getting user by ID");
		log.debug("get method takes User ID as parameter");
		return mapper.map(getUserIfExsists(id), UserDTO.class);
	}

	@Override
	public List<UserDTO> getAll(Pageable pageable) throws ZeroUsersException {
		log.info("In method getAll, for getting all the users");
		log.debug("getAll method takes Pageable interface as parameter");
		Page<User> users = userRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
		if (users.getNumberOfElements() == 0) {
			throw new ZeroUsersException("No users available to display on this page");
		}
		log.debug("getAll method returning the list of UserDTO's");
		return users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
	}

	@Override
	public UserDTO add(UserDTO userDTO) {
		log.info("In method add, for adding a user");
		log.debug("add method takes UserDTO as parameter");
		User user = mapper.map(userDTO, User.class);
		userRepository.save(user);
		log.debug("add method returning the UserDTO of added user");
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public void delete(int id) throws NoSuchUserException {
		log.info("In method delete, for deleting a user by ID");
		log.debug("delete method takes User ID as parameter");
		checkIfUserExsists(id);
		userRepository.deleteById(id);
		log.info("User is deleted");
	}

	private void checkIfUserExsists(int id) throws NoSuchUserException {
		log.debug("In method checkIfUserExsists that takes User ID as parameter");
		if (!userRepository.existsById(id)) {
			throw new NoSuchUserException("User not found. Please enter a valid ID");
		}
	}

	@Override
	public UserDTO update(UserDTO userDTO) throws NoSuchUserException {
		log.info("In method update, for updating a user details");
		log.debug("update method takes UserDTO as parameter and gets the user by ID");
		User user = getUserIfExsists(userDTO.getId());
		updateChangedFeilds(user, userDTO);
		log.info("Saving the new user details");
		userRepository.save(user);
		log.debug("update method returning the UserDTO of updated user");
		return mapper.map(user, UserDTO.class);
	}

	private User getUserIfExsists(int id) throws NoSuchUserException {
		log.debug("In method getUserIfExsists that takes User ID as parameter");
		return userRepository.findById(id)
				.orElseThrow(() -> new NoSuchUserException("User not found. Please enter a valid ID"));
	}

	private void updateChangedFeilds(User user, UserDTO userDTO) {
		log.debug("In method updateChangedFeilds that takes User and corresponding DTO as parameters");
		Object temp;
		temp = userDTO.getName();
		if (temp != null && (!user.getName().equals(temp))) {
			user.setName((String) temp);
		}

		temp = userDTO.getEmail();
		if (temp != null && (!user.getEmail().equals(temp))) {
			user.setEmail((String) temp);
		}

		temp = userDTO.getPhoneNo();
		if (temp != null && (!user.getPhoneNo().equals(temp))) {
			user.setPhoneNo((String) temp);
		}

		log.debug("Updated the changed feilds");
	}

}
