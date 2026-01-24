package com.hms.healthcare.service;

import java.util.Map;

public interface PatientService {

	Map<String, Object> getDoctors(String name, String specialization, int size, int page, boolean desc, String sort);

	Map<String, Object> getDoctorsTimeSlot(Long id);

	Map<String, Object> bookAppointment(Long id, String email);

	Map<String, Object> viewAppointments(String email);

	Map<String, Object> createPayment(String name, Long patientId, Long id);

	Map<String, Object> confirmPayment(Long id, String razaorpay_payment_id);

	Map<String, Object> viewHistory(String email);

}
