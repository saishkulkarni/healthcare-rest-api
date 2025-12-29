package com.hms.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

}