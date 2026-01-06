package com.hms.healthcare.dao;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Receptionist;
import com.hms.healthcare.repository.ReceptionistRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReceptionistDao {

	private final ReceptionistRepository receptionistRepository;

	public void save(Receptionist recceptionist) {
		receptionistRepository.save(recceptionist);
	}

}
