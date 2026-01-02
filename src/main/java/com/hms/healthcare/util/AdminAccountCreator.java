package com.hms.healthcare.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hms.healthcare.entity.User;
import com.hms.healthcare.enums.HospitalRoles;
import com.hms.healthcare.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAccountCreator implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.password}")
	private String adminPassword;

	@Override
	public void run(String... args) throws Exception {
		if (!userRepository.existsByEmail(adminEmail)) {
			log.info("Creating default admin account");
			User adminUser = new User(null, "Administrator", adminEmail, passwordEncoder.encode(adminPassword), 0L,
					HospitalRoles.ADMIN, true, null);
			userRepository.save(adminUser);
			log.info("Default admin account created with email: {}", adminEmail);
		}
	}
}