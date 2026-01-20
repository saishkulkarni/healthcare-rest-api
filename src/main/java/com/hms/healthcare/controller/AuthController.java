package com.hms.healthcare.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hms.healthcare.dto.LoginDto;
import com.hms.healthcare.dto.OtpDto;
import com.hms.healthcare.dto.PasswordDto;
import com.hms.healthcare.dto.PatientDto;
import com.hms.healthcare.dto.ResetPasswordDto;
import com.hms.healthcare.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> login(@Valid @RequestBody LoginDto loginDto) {
		return authService.login(loginDto);
	}

	@PatchMapping("/password")
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST','PATIENT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> changePassword(@Valid @RequestBody PasswordDto adminPasswordDto, Principal principal) {
		return authService.changePassword(adminPasswordDto, principal);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> register(@Valid @RequestBody PatientDto patientDto) {
		return authService.register(patientDto);
	}

	@PatchMapping("/otp")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> verifyOtp(@Valid @RequestBody OtpDto otpDto) {
		return authService.verifyOtp(otpDto);
	}

	@PatchMapping("/otp/{email}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> resendOtp(@PathVariable String email) {
		return authService.resendOtp(email);
	}

	@PatchMapping("/forgot-password/{email}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> forgotPassword(@PathVariable String email) {
		return authService.forgotPassword(email);
	}

	@PatchMapping("/reset-password")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
		return authService.resetPassword(resetPasswordDto);
	}

	@GetMapping("/me")
	@PreAuthorize("hasAnyRole('PATIENT','ADMIN','DOCTOR','RECEPTIONIST')")
	public Map<String, Object> viewProfile(Principal principal) {
		return authService.viewProfile(principal.getName());
	}
}
