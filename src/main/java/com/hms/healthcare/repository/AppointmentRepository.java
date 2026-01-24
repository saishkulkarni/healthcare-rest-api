package com.hms.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPatient(Patient patient);

	List<Appointment> findByDoctor(Doctor doctor);


}
