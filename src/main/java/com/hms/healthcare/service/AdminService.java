package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import com.hms.healthcare.dto.AdminPasswordDto;
import com.hms.healthcare.dto.DoctorDto;

public interface AdminService {

	Map<String, Object> changePassword(AdminPasswordDto adminPasswordDto, Principal principal);

	Map<String, Object> enrollDoctor(DoctorDto doctorDto);

}
