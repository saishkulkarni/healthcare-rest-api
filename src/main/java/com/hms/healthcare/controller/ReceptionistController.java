package com.hms.healthcare.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.healthcare.dto.TimeSlotRequestDto;
import com.hms.healthcare.service.ReceptionistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

	private final ReceptionistService receptionistService;

	@GetMapping("/doctors")
	@PreAuthorize("hasRole('RECEPTIONIST')")
	public Map<String, Object> getDoctors() {
		return receptionistService.getAllDoctors();
	}

	@GetMapping("/doctors/{id}")
	@PreAuthorize("hasRole('RECEPTIONIST')")
	public Map<String, Object> getDoctorsSlot(@PathVariable Long id) {
		return receptionistService.getDoctorsSlot(id);
	}

	@PostMapping("/doctors/timeslots")
	@PreAuthorize("hasRole('RECEPTIONIST')")
	public Map<String, Object> addDoctorsSlot(@RequestBody TimeSlotRequestDto requestDto) {
		return receptionistService.addDoctorsSlot(requestDto);
	}
	
	@GetMapping("/appointments")
	@PreAuthorize("hasRole('RECEPTIONIST')")
	public Map<String, Object> viewAppointments() {
		return receptionistService.viewAllAppointments();
	}
}
