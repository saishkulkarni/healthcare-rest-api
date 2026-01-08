package com.hms.healthcare.service;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.ReceptionistDao;
import com.hms.healthcare.dao.UserDao;
import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.ReceptionistDto;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.Receptionist;
import com.hms.healthcare.entity.User;
import com.hms.healthcare.enums.HospitalRoles;
import com.hms.healthcare.mapper.DoctorMapper;
import com.hms.healthcare.util.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final DoctorDao doctorDao;
	private final ReceptionistDao receptionistDao;
	private final EmailService emailService;
	private final DoctorMapper doctorMapper;

	@Override
	public Map<String, Object> enrollDoctor(DoctorDto doctorDto) {
		if (userDao.checkDuplicateEmailAndMobile(doctorDto.getEmail(), doctorDto.getPhoneNumber())) {
			throw new IllegalArgumentException("User with given email or mobile already exists");
		}
		User user = new User(null, doctorDto.getName(), doctorDto.getEmail(),
				passwordEncoder.encode(doctorDto.getPassword()), doctorDto.getPhoneNumber(), HospitalRoles.DOCTOR, true,
				null);
		userDao.save(user);
		Doctor doctor = new Doctor(null, doctorDto.getName(), doctorDto.getPhoneNumber(), doctorDto.getSpecialization(),
				doctorDto.getExperience(), doctorDto.getAddress(), doctorDto.getLicenceNumber(), user);
		doctorDao.save(doctor);
		emailService.sendConfirmation(user.getEmail(), doctorDto.getPassword(), "DOCTOR", doctorDto.getName());
		return Map.of("message", "Doctor Enrolled Successfully", "doctor", doctor);
	}

	@Override
	public Map<String, Object> enrollReceptionist(ReceptionistDto receptionistDto) {
		if (userDao.checkDuplicateEmailAndMobile(receptionistDto.getEmail(), receptionistDto.getPhoneNumber())) {
			throw new IllegalArgumentException("User with given email or mobile already exists");
		}
		User user = new User(null, receptionistDto.getName(), receptionistDto.getEmail(),
				passwordEncoder.encode(receptionistDto.getPassword()), receptionistDto.getPhoneNumber(),
				HospitalRoles.RECEPTIONIST, true, null);
		userDao.save(user);
		Receptionist recceptionist = new Receptionist(null, receptionistDto.getName(), receptionistDto.getPhoneNumber(),
				receptionistDto.getAddress(), user);
		receptionistDao.save(recceptionist);
		emailService.sendConfirmation(user.getEmail(), receptionistDto.getPassword(), "RECEPTIONIST",
				receptionistDto.getName());
		return Map.of("message", "Receptionist Enrolled Successfully", "receptionist", recceptionist);
	}

	@Override
	public Map<String, Object> getAllDoctors() {
		return Map.of("message", "Doctors Found", "doctors", doctorMapper.toDoctorDtoList(doctorDao.findAll()));
	}
}
