package com.hms.healthcare.service;

import java.util.Map;

import com.hms.healthcare.dto.LoginDto;

public interface AuthService {

	Map<String, Object> login(LoginDto loginDto);

}
