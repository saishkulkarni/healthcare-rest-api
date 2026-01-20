package com.hms.healthcare.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class AppointmentDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long appointmentId;
	private String patientName;
	private String doctorName;
	private LocalDateTime appointmentTime;
	private String paymentStatus;
}
