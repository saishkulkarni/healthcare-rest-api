package com.hms.healthcare.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Receptionist;
import com.hms.healthcare.exception.DataNotFoundException;
import com.hms.healthcare.repository.AppointmentRepository;
import com.hms.healthcare.repository.ReceptionistRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReceptionistDao {

	private final ReceptionistRepository receptionistRepository;
	private final AppointmentRepository appointmentRepository;

	public void save(Receptionist recceptionist) {
		receptionistRepository.save(recceptionist);
	}

	public List<Receptionist> findAll() {
		List<Receptionist> receptionists = receptionistRepository.findAll();
		if (receptionists.isEmpty())
			throw new IllegalArgumentException("No receptionists found");
		return receptionists;
	}

	public List<Appointment> findAllAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll();
		if (appointments.isEmpty())
			throw new DataNotFoundException("No Apppointments Scheduled Yet");
		return appointments;
	}

}
