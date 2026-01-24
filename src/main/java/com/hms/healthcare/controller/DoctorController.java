package com.hms.healthcare.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.healthcare.dto.MedicalHistoryDto;
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

	@PostMapping("/patient/appointment/{id}")
	@PreAuthorize("hasRole('DOCTOR')")
	public Map<String, Object> addMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistoryDto historyDto) {
		return doctorService.addMedicalHistory(id, historyDto);
	}
}
