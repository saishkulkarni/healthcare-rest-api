package com.hms.healthcare.service;

import java.util.Map;

public interface PatientService {

	Map<String, Object> getDoctors(String name, String specialization, int size, int page, boolean desc, String sort);

	Map<String, Object> getDoctorsTimeSlot(Long id);

}
