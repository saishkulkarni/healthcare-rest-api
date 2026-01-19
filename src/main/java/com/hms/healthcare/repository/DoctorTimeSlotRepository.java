package com.hms.healthcare.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;

public interface DoctorTimeSlotRepository extends JpaRepository<DoctorTimeSlot, Long> {

	List<DoctorTimeSlot> findByDoctor(Doctor doctor);

	boolean existsByTimeSlotAndDoctor(LocalDateTime timeSlot, Doctor doctor);

	List<DoctorTimeSlot> findByDoctorAndBookedFalse(Doctor doctor);

	List<DoctorTimeSlot> findByDoctorAndBookedFalseAndTimeSlotAfter(Doctor doctor, LocalDateTime now);

}
