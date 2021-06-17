package com.epam.user.dto;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	int id;

	String name;

	String email;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should contain 10 digits only")
	String phoneNo;

}
