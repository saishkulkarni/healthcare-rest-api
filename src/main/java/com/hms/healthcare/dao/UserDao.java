package com.hms.healthcare.dao;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.User;
import com.hms.healthcare.exception.DataNotFoundException;
import com.hms.healthcare.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final UserRepository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public boolean checkDuplicateEmailAndMobile(String email, Long mobile) {
		return userRepository.existsByEmailOrMobile(email, mobile);
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id :" + id));
	}

}
