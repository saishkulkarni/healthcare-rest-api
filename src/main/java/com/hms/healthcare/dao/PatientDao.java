package com.hms.healthcare.dao;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PatientDao {

	private final PatientRepository patientRepository;

	public void save(Patient patient) {
		patientRepository.save(patient);
	}
}
