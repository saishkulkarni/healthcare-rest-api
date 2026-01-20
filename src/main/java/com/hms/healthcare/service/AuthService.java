package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import com.hms.healthcare.dto.LoginDto;
import com.hms.healthcare.dto.OtpDto;
import com.hms.healthcare.dto.PasswordDto;
import com.hms.healthcare.dto.PatientDto;
import com.hms.healthcare.dto.ResetPasswordDto;

public interface AuthService {

	Map<String, Object> changePassword(PasswordDto passwordDto, Principal principal);

	Map<String, Object> login(LoginDto loginDto);

	Map<String, Object> register(PatientDto patientDto);

	Map<String, Object> verifyOtp(OtpDto otpDto);

	Map<String, Object> resendOtp(String email);

	Map<String, Object> forgotPassword(String email);

	Map<String, Object> resetPassword(ResetPasswordDto resetPasswordDto);

	Map<String, Object> viewProfile(String name);

}
