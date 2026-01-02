package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.UserDao;
import com.hms.healthcare.dto.AdminPasswordDto;
import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.entity.Doctor;
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
	private final UserMapper userMapper;
	private final DoctorDao doctorDao;
	private final EmailService emailService;

	@Override
	public Map<String, Object> changePassword(AdminPasswordDto adminPasswordDto, Principal principal) {
		String email = principal.getName();
		User user = userDao.findByEmail(email);
		if (passwordEncoder.matches(adminPasswordDto.getPrevPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(adminPasswordDto.getNewPassword()));
			userDao.save(user);
			return Map.of("message", "Password Updated Success", "user", userMapper.toUserResponseDto(user));
		} else {
			throw new IllegalArgumentException("Previous password is incorrect");
		}
	}

	@Override
	public Map<String, Object> enrollDoctor(DoctorDto doctorDto) {
		if (userDao.checkEmailAndMobile(doctorDto.getName(), doctorDto.getPhoneNumber())) {
			throw new IllegalArgumentException("Doctor with given email or mobile already exists");
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
}
