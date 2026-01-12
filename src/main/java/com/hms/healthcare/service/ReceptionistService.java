package com.hms.healthcare.service;

import java.util.Map;

public interface ReceptionistService {

	Map<String, Object> getAllDoctors();

	Map<String, Object> getDoctorsSlot(Long id);

}
