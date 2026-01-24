package com.hms.healthcare.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.MedicalHistory;
import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.entity.User;
import com.hms.healthcare.exception.DataNotFoundException;
import com.hms.healthcare.repository.AppointmentRepository;
import com.hms.healthcare.repository.MedicalHistoryRepository;
import com.hms.healthcare.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PatientDao {

	private final PatientRepository patientRepository;
	private final UserDao userDao;
	private final AppointmentRepository appointmentRepository;
	private final MedicalHistoryRepository medicalHistoryRepository;

	public void save(Patient patient) {
		patientRepository.save(patient);
	}

	public List<Patient> findAll() {
		List<Patient> patients = patientRepository.findAll();
		if (patients.isEmpty())
			throw new DataNotFoundException("No Patients Record Found");
		return patients;
	}

	public Patient findPatientByEmail(String email) {
		User user = userDao.findByEmail(email);
		return patientRepository.findByUser(user)
				.orElseThrow(() -> new DataNotFoundException("No Patient Record Found"));
	}

	public void saveAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}

	public List<Appointment> getAppointments(Patient patient) {
		List<Appointment> appointments = appointmentRepository.findByPatient(patient);
		if (appointments.isEmpty())
			throw new DataNotFoundException("No Appointments Scheduled yet");
		return appointments;
	}

	public Patient findById(Long id) {
		return patientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Patient Record Not Found"));
	}

	public Appointment getAppointmentFromId(Long id) {
		return appointmentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Appointment Not Found"));
	}

	public void saveMedicalHistory(MedicalHistory history) {
		medicalHistoryRepository.save(history);
	}

	public boolean checkIfAlreadyMedicalHistoryPresent(Long id) {
		return medicalHistoryRepository.existsByAppointmentId(id);
	}

	public List<MedicalHistory> getMedicalHistory(Patient patient) {
		List<MedicalHistory> histories = medicalHistoryRepository.findByPatient(patient);
		if (histories.isEmpty())
			throw new DataNotFoundException("No Medical History Found..");
		return histories;
	}

}
