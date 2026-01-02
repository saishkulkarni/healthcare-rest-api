package com.hms.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
