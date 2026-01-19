package com.hms.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
	public class DoctorDto {
		@NotEmpty(message = "Name is required")
		private String name;
		@NotNull(message = "Experience is required")
		private Integer experience;
		@NotEmpty(message = "Specialization is required")
		private String specialization;
		@NotEmpty(message = "Email is required")
		private String email;
		@NotNull(message = "Phone number is required")
		private Long phoneNumber;
		@NotEmpty(message = "Password is required")
		private String password;
		@NotEmpty(message = "Licence number is required")
		private String licenceNumber;
		@NotEmpty(message = "Address is required")
		private String address;
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		private Long userId;
		@JsonProperty(access = Access.READ_ONLY)
		private boolean status;
	}
