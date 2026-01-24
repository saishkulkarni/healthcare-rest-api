package com.hms.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.MedicalHistory;
import com.hms.healthcare.entity.Patient;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

	boolean existsByAppointmentId(Long id);

	List<MedicalHistory> findByPatient(Patient patient);

}
