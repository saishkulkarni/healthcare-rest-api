package com.hms.healthcare.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;

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
		List<DoctorTimeSlot> timeSlots = doctorDao.getDoctorsTimeSlot(doctor);
		return Map.of("message", "Doctor AVailable at Below Time Slots", "timeSlots",
				userMapper.toTimeSlotDtoList(timeSlots));
	}

}
