package com.hms.healthcare.dto;

import lombok.Data;

@Data
public class UserResponseDto {
	private String email;
	private String username;
	private Long mobile;
	private String role;
}