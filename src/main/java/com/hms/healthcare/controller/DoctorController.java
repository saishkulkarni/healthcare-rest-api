package com.hms.healthcare.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.healthcare.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
	private final DoctorService doctorService;

	@GetMapping("/appointments")
	@PreAuthorize("hasRole('DOCTOR')")
	public Map<String, Object> viewAppointments(Principal principal) {
		return doctorService.viewAppointments(principal.getName());
	}
}
