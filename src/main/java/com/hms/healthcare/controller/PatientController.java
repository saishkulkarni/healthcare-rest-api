package com.hms.healthcare.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public Map<String, Object> bookAppointment(@PathVariable Long id, Principal principal) {
		return patientService.bookAppointment(id, principal.getName());
	}

	@GetMapping("/appointments")
	@PreAuthorize("hasRole('PATIENT')")
	public Map<String, Object> viewAppointments(Principal principal) {
		return patientService.viewAppointments(principal.getName());
	}

	@PostMapping("/payment/{id}")
	@PreAuthorize("hasAnyRole('PATIENT','RECEPTIONIST')")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> initiatePayment(Principal principal, @RequestParam(defaultValue =  "0") Long patientId,
			@PathVariable Long id) {
		return patientService.createPayment(principal.getName(), patientId, id);
	}
	
	@PostMapping("/payment/confirm/{id}")
	public Map<String, Object> confirmPayment(@RequestParam String razorpay_payment_id,
			@PathVariable Long id) {
		return patientService.confirmPayment(id,razorpay_payment_id);
	}
	
	@GetMapping("/history")
	@PreAuthorize("hasRole('PATIENT')")
	public Map<String, Object> viewHistory(Principal principal){
		return patientService.viewHistory(principal.getName());
	}
}
