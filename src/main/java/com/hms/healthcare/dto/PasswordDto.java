package com.hms.healthcare.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordDto {
	@NotEmpty(message = "Previous password must not be empty")
	private String prevPassword;
	@NotEmpty(message = "New password must not be empty")
	private String newPassword;
}
