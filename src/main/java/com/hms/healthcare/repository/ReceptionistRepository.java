package com.hms.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.healthcare.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {

}
