package com.hms.healthcare.util;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hms.healthcare.dto.PatientDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String OTP_PREFIX = "OTP_";
	private static final String PENDING_USER_PREFIX = "PENDING_USER_";

	@Async
	public void storeOtp(String email, Integer otp) {
		redisTemplate.opsForValue().set(OTP_PREFIX + email, otp, Duration.ofMinutes(5));
	}

	@Async
	public void storePendingUser(String email, PatientDto patientDto) {
		redisTemplate.opsForValue().set(PENDING_USER_PREFIX + email, patientDto, Duration.ofMinutes(30));
	}

	public Integer getOtp(String email) {
		return (Integer) redisTemplate.opsForValue().get(OTP_PREFIX + email);
	}

	public PatientDto getPendingUser(String email) {
		return (PatientDto) redisTemplate.opsForValue().get(PENDING_USER_PREFIX + email);
	}

	public boolean isPendingUserExists(String email) {
		if (getPendingUser(email) != null) {
			return true;
		}
		return false;
	}

}
