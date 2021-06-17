package com.epam.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
