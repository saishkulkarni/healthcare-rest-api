package com.hms.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
