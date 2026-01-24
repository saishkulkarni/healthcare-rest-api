package com.hms.healthcare.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String diagnosis;
	private String presecription;
	private String scanning;
	private String suggestions;
	private LocalDate visitDate;
	private LocalDate followUpDate;
	private Long appointmentId;
	private String doctorName;
	@ManyToOne
	Patient patient;
}
