package com.hms.healthcare.service;

import java.util.Map;

import com.hms.healthcare.dto.MedicalHistoryDto;

public interface DoctorService {

	Map<String, Object> viewAppointments(String email);

	Map<String, Object> addMedicalHistory(Long id, MedicalHistoryDto historyDto);

}
