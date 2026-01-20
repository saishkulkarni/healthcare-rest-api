package com.hms.healthcare.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.PatientDao;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;
	private final PatientDao patientDao;

	@Override
	public Map<String, Object> getDoctors(String name, String specialization, int size, int page, boolean desc,
			String sort) {
		List<Doctor> doctors = null;
		if (name == null && specialization == null)
			doctors = doctorDao.getAllDoctors(page, size, sort, desc);
		else {
			if (name != null && specialization != null)
				doctors = doctorDao.findByNameAndSpecialization(name, specialization);
			else {
				if (name != null)
					doctors = doctorDao.findByName(name);
				else
					doctors = doctorDao.findBySpecialization(specialization);
			}
		}
		return Map.of("message", "Doctors Records Found", "doctors", userMapper.toDoctorDtoList(doctors));
	}

	@Override
	public Map<String, Object> getDoctorsTimeSlot(Long id) {
		Doctor doctor = doctorDao.getByUserId(id);
		List<DoctorTimeSlot> timeSlots = doctorDao.getDoctorsAvailableTimeSlot(doctor);
		return Map.of("message", "Doctor AVailable at Below Time Slots", "timeSlots",
				userMapper.toTimeSlotDtoList(timeSlots));
	}

	@Override
	public Map<String, Object> bookAppointment(Long id, String email) {
		Patient patient = patientDao.findPatientByEmail(email);
		DoctorTimeSlot doctorTimeSlot = doctorDao.getDoctorTimeSlotById(id);
		if (doctorTimeSlot.isBooked())
			throw new IllegalArgumentException("Already Slot Booked");
		Appointment appointment = new Appointment(null, doctorTimeSlot.getTimeSlot(), doctorTimeSlot.getDoctor(),
				patient, false);
		patientDao.saveAppointment(appointment);
		doctorTimeSlot.setBooked(true);
		doctorDao.saveTimeSlot(doctorTimeSlot);
		return Map.of("message", "Appointment Booked Success", "appointment", userMapper.toAppointmentDto(appointment));
	}

	@Override
	public Map<String, Object> viewAppointments(String email) {
		Patient patient = patientDao.findPatientByEmail(email);
		List<Appointment> appointments = patientDao.getAppointments(patient);
		return Map.of("message", "Appoinments Found Success", "appointments",
				userMapper.toAppointmentDtoList(appointments));
	}

}
