package com.hms.healthcare.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hms.healthcare.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@GetMapping("/doctors")
	@PreAuthorize("hasRole('PATIENT')")
	public Map<String, Object> getDoctors(@RequestParam(required = false) String name,
			@RequestParam(required = false) String specialization, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc) {
		return patientService.getDoctors(name, specialization, size, page, desc, sort);
	}

	@GetMapping("/doctors/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public Map<String, Object> getDoctorsTimeSlot(@PathVariable Long id) {
		return patientService.getDoctorsTimeSlot(id);
	}

	@PostMapping("/appointments/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public Map<String, Object> bookAppointment(@PathVariable Long id,Principal principal) {
		return patientService.bookAppointment(id,principal.getName());
	}

}
