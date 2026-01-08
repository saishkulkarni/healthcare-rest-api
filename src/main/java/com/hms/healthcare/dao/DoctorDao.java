package com.hms.healthcare.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DoctorDao {
	private final DoctorRepository doctorRepository;

	public void save(Doctor doctor) {
		doctorRepository.save(doctor);
	}

	public List<Doctor> findAll() {
		List<Doctor> doctors = doctorRepository.findAll();
		if (doctors.isEmpty())
			throw new IllegalArgumentException("No doctors found");
		return doctors;
	}
}
