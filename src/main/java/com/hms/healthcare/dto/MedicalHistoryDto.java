package com.hms.healthcare.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MedicalHistoryDto {
	private String diagnosis;
	private String prescription;
	private String scanning;
	private String suggestions;
	private LocalDate followUpDate;
}
