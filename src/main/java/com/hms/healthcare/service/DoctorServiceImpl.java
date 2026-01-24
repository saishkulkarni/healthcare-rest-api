package com.hms.healthcare.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.PatientDao;
import com.hms.healthcare.dto.MedicalHistoryDto;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.MedicalHistory;
import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;
	private final PatientDao patientDao;

	@Override
	public Map<String, Object> viewAppointments(String email) {
		Doctor doctor = doctorDao.findByEmail(email);
		List<Appointment> appointments = doctorDao.getAppointments(doctor);
		return Map.of("message", "Appoinments Found Success", "appointments",
				userMapper.toAppointmentDtoList(appointments));
	}

	@Override
	public Map<String, Object> addMedicalHistory(Long id, MedicalHistoryDto historyDto) {
		Appointment appointment = patientDao.getAppointmentFromId(id);
		if (patientDao.checkIfAlreadyMedicalHistoryPresent(id))
			throw new IllegalArgumentException("Already Exists");
		if (!appointment.isPaymentStatus())
			throw new IllegalArgumentException("Payment Not Done Yet");
		appointment.setDone(true);
		Patient patient = appointment.getPatient();
		MedicalHistory history = new MedicalHistory(null, historyDto.getDiagnosis(), historyDto.getPrescription(),
				historyDto.getScanning(), historyDto.getSuggestions(), LocalDate.now(), historyDto.getFollowUpDate(),
				id,appointment.getDoctor().getName() ,patient);

		patientDao.saveMedicalHistory(history);
		return Map.of("message", "Patient Record Updated Success", "medical", history);
	}

}
