package com.hms.healthcare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
	@NotEmpty(message = "* Email cannot be Empty")
	@Email(message = "* Invalid email format")
	private String email;
	@NotEmpty(message = "* Password cannot be Empty")
	private String password;
}
