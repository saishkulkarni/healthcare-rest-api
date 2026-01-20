package com.hms.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.User;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Optional<Doctor> findByUser_id(Long id);

	List<Doctor> findByNameAndSpecialization(String name, String specialization);

	List<Doctor> findBySpecialization(String specialization);

	List<Doctor> findByNameContains(String name);

	Optional<Doctor> findByUser(User user);

}
