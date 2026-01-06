package com.hms.healthcare.service;

import java.security.Principal;
import java.util.Map;

import com.hms.healthcare.dto.LoginDto;
import com.hms.healthcare.dto.PasswordDto;

public interface AuthService {

	Map<String, Object> changePassword(PasswordDto passwordDto, Principal principal);

	Map<String, Object> login(LoginDto loginDto);

}
