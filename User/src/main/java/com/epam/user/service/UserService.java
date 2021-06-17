package com.epam.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.epam.user.dto.UserDTO;
import com.epam.user.exception.NoSuchUserException;
import com.epam.user.exception.ZeroUsersException;

public interface UserService {

	UserDTO get(int id) throws NoSuchUserException;

	List<UserDTO> getAll(Pageable pageable) throws ZeroUsersException;

	UserDTO add(UserDTO userDTO);

	void delete(int id) throws NoSuchUserException;

	UserDTO update(UserDTO userDTO) throws NoSuchUserException;
}
