package com.hms.healthcare.service;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.PatientDao;
import com.hms.healthcare.dao.ReceptionistDao;
import com.hms.healthcare.dao.UserDao;
import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.ReceptionistDto;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.Receptionist;
import com.hms.healthcare.entity.User;
import com.hms.healthcare.enums.HospitalRoles;
import com.hms.healthcare.mapper.UserMapper;
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
	private final UserMapper userMapper;
	private final PatientDao patientDao;

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
		return Map.of("message", "Doctors Found", "doctors", userMapper.toDoctorDtoList(doctorDao.findAll()));
	}

	@Override
	public Map<String, Object> getAllReceptionists() {
		return Map.of("message", "Receptionists Found", "receptionists",
				userMapper.toReceptionistDtoList(receptionistDao.findAll()));
	}

	@Override
	public Map<String, Object> getAllPatients() {
		return Map.of("message", "Patients Found", "patients", userMapper.toPatientsDtoList(patientDao.findAll()));
	}

	@Override
	public Map<String, Object> blockUser(Long id) {
		User user = userDao.findById(id);
		if (!user.getRole().equals(HospitalRoles.ADMIN)) {
			user.setIsActive(false);
			userDao.save(user);
		} else
			throw new IllegalArgumentException("You can not Block Admin Account");
		return Map.of("message", "User Block Succcess", "user", userMapper.toUserResponseDto(user));
	}

	@Override
	public Map<String, Object> unblockUser(Long id) {
		User user = userDao.findById(id);
		if (!user.getRole().equals(HospitalRoles.ADMIN)) {
			user.setIsActive(true);
			userDao.save(user);
		} else
			throw new IllegalArgumentException("Admin Account is Not Blocked");

		return Map.of("message", "User Unblocked Succcess", "user", userMapper.toUserResponseDto(user));
	}

}