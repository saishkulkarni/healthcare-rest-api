package com.hms.healthcare.controller;

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

import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.ReceptionistDto;
import com.hms.healthcare.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/enroll/doctor")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> enrollDoctor(@Valid @RequestBody DoctorDto doctorDto) {
		return adminService.enrollDoctor(doctorDto);
	}

	@PostMapping("/enroll/receptionist")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> enrollReceptionist(@Valid @RequestBody ReceptionistDto receptionistDto) {
		return adminService.enrollReceptionist(receptionistDto);
	}

	@GetMapping("/doctors")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getAllDoctors() {
		return adminService.getAllDoctors();
	}

	@GetMapping("/receptionists")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getAllReceptionists() {
		return adminService.getAllReceptionists();
	}
	
	@GetMapping("/patients")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getAllPatients() {
		return adminService.getAllPatients();
	}
	
	@PatchMapping("/block/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> blockUser(@PathVariable Long id) {
		return adminService.blockUser(id);
	}

	
	@PatchMapping("/unblock/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> unblockUser(@PathVariable Long id) {
		return adminService.unblockUser(id);
	}

}
