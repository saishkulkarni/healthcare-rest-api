package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import com.hms.healthcare.dto.AdminPasswordDto;

public interface AdminService {

	Map<String, Object> changePassword(AdminPasswordDto adminPasswordDto, Principal principal);

}
