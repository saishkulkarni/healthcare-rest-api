package com.hms.healthcare.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.exception.DataNotFoundException;
import com.hms.healthcare.repository.DoctorRepository;
import com.hms.healthcare.repository.DoctorTimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DoctorDao {
	private final DoctorRepository doctorRepository;
	private final DoctorTimeSlotRepository doctorTimeSlotRepository;

	public void save(Doctor doctor) {
		doctorRepository.save(doctor);
	}

	public List<Doctor> findAll() {
		List<Doctor> doctors = doctorRepository.findAll();
		if (doctors.isEmpty())
			throw new IllegalArgumentException("No doctors found");
		return doctors;
	}

	public Doctor getByUserId(Long id) {
		return doctorRepository.findByUser_id(id)
				.orElseThrow(() -> new DataNotFoundException("No Doctor Record with Id: " + id));
	}

	public List<DoctorTimeSlot> getDoctorsTimeSlot(Doctor doctor) {
		List<DoctorTimeSlot> timeSlots = doctorTimeSlotRepository.findByDoctor(doctor);
		if (timeSlots.isEmpty())
			throw new DataNotFoundException("No Time Slots Alloted for Doctor " + doctor.getName());
		return timeSlots;
	}
}
