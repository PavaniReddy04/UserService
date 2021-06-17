package com.epam.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@NotNull(message = "Name can't be null")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	String name;
	
	@NotEmpty(message = "E-mail can't be empty")
	@Email(message = "Please enter a valid email")
	String email;
	
	@Column(name = "phoneNumber")
	String phoneNo;

}
