package com.hms.healthcare.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;

	@Override
	public Map<String, Object> viewAppointments(String email) {
		Doctor doctor = doctorDao.findByEmail(email);
		List<Appointment> appointments = doctorDao.getAppointments(doctor);
		return Map.of("message", "Appoinments Found Success", "appointments",
				userMapper.toAppointmentDtoList(appointments));

	}

}
