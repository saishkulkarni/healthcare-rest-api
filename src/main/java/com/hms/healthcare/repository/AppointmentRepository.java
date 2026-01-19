package com.hms.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
