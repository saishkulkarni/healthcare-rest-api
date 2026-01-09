package com.hms.healthcare.service;

import java.util.Map;

import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.ReceptionistDto;

public interface AdminService {

	Map<String, Object> enrollDoctor(DoctorDto doctorDto);

	Map<String, Object> enrollReceptionist(ReceptionistDto receptionistDto);

	Map<String, Object> getAllDoctors();

	Map<String, Object> getAllReceptionists();

	Map<String, Object> getAllPatients();

	Map<String, Object> blockUser(Long id);

	Map<String, Object> unblockUser(Long id);

}
