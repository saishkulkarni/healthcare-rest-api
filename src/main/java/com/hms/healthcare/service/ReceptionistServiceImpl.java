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
public class ReceptionistServiceImpl implements ReceptionistService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;

	@Override
	public Map<String, Object> getAllDoctors() {
		List<Doctor> doctors = doctorDao.findAll();
		return Map.of("message", "Doctos Found", "doctors", userMapper.toDoctorDtoList(doctors));
	}

	@Override
	public Map<String, Object> getDoctorsSlot(Long id) {
		Doctor doctor = doctorDao.getByUserId(id);
		List<DoctorTimeSlot> timeSlots = doctorDao.getDoctorsTimeSlot(doctor);
		return Map.of("message", "TimeSlots Found", "slots", userMapper.toTimeSlotDtoList(timeSlots));
	}

}
