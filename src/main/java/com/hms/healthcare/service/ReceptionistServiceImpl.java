package com.hms.healthcare.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.ReceptionistDao;
import com.hms.healthcare.dto.TimeSlotRequestDto;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceptionistServiceImpl implements ReceptionistService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;
	private final ReceptionistDao receptionistDao;

	@Override
	public Map<String, Object> getAllDoctors() {
		List<Doctor> doctors = doctorDao.findAll();
		return Map.of("message", "Doctos Found", "doctors", userMapper.toDoctorDtoList(doctors));
	}

	@Override
	public Map<String, Object> getDoctorsSlot(Long id) {
		Doctor doctor = doctorDao.getByUserId(id);
		List<DoctorTimeSlot> timeSlots = doctorDao.getDoctorsAvailableTimeSlot(doctor);
		return Map.of("message", "TimeSlots Found", "slots", userMapper.toTimeSlotDtoList(timeSlots));
	}

	@Override
	public Map<String, Object> addDoctorsSlot(TimeSlotRequestDto requestDto) {
		Doctor doctor = doctorDao.getByUserId(requestDto.getUserId());
		DoctorTimeSlot doctorTimeSlot = userMapper.toDotorTimeSlot(requestDto, doctor);
		doctorDao.saveTimeSlot(doctorTimeSlot);
		return Map.of("message", "Slot Added Success", "timeSlot", userMapper.toTimeSlotDto(doctorTimeSlot));
	}

	@Override
	public Map<String, Object> viewAllAppointments() {
		List<Appointment> appointments = receptionistDao.findAllAppointments();
		return Map.of("message", "Appointments Found", "appointments", userMapper.toAppointmentDtoList(appointments));
	}

}
