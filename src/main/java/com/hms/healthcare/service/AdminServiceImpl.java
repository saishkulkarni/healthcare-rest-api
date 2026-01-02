package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.UserDao;
import com.hms.healthcare.dto.AdminPasswordDto;
import com.hms.healthcare.entity.User;
import com.hms.healthcare.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

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
}
