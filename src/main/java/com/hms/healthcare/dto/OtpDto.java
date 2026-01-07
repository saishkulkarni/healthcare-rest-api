package com.hms.healthcare.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OtpDto {
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	@NotNull(message = "OTP cannot be empty")
	private Integer otp;
}
