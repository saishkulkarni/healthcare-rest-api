package com.hms.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.entity.User;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByUser(User user);

}
