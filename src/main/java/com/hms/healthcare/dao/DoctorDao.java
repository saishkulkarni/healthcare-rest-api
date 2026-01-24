package com.hms.healthcare.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.entity.User;
import com.hms.healthcare.exception.DataNotFoundException;
import com.hms.healthcare.repository.AppointmentRepository;
import com.hms.healthcare.repository.DoctorRepository;
import com.hms.healthcare.repository.DoctorTimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DoctorDao {
	private final DoctorRepository doctorRepository;
	private final DoctorTimeSlotRepository doctorTimeSlotRepository;
	private final UserDao userDao;
	private final AppointmentRepository appointmentRepository;

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

	public List<DoctorTimeSlot> getDoctorsAvailableTimeSlot(Doctor doctor) {
		List<DoctorTimeSlot> timeSlots = doctorTimeSlotRepository.findByDoctorAndBookedFalseAndTimeSlotAfter(doctor,
				LocalDateTime.now());
		if (timeSlots.isEmpty())
			throw new DataNotFoundException("No Time Slots Alloted for Doctor " + doctor.getName());
		return timeSlots;
	}

	public void saveTimeSlot(DoctorTimeSlot doctorTimeSlot) {
		if (!doctorTimeSlotRepository.existsByTimeSlotAndDoctor(doctorTimeSlot.getTimeSlot(),
				doctorTimeSlot.getDoctor())) {
			doctorTimeSlotRepository.save(doctorTimeSlot);
		} else
			throw new IllegalArgumentException("Already Slot Added");
	}

	public List<Doctor> getAllDoctors(int page, int size, String sort, boolean desc) {
		List<Doctor> doctors = doctorRepository
				.findAll(PageRequest.of(page - 1, size, desc ? Sort.by(sort).descending() : Sort.by(sort)))
				.getContent();
		if (doctors.isEmpty())
			throw new DataNotFoundException("No Doctors Record Found");
		else
			return doctors;
	}

	public List<Doctor> findByNameAndSpecialization(String name, String specialization) {
		List<Doctor> doctors = doctorRepository.findByNameAndSpecialization(name, specialization);
		if (doctors.isEmpty())
			throw new DataNotFoundException(
					"No Doctors Record Found with Name: " + name + " having specialization in " + specialization);
		else
			return doctors;
	}

	public List<Doctor> findByName(String name) {
		List<Doctor> doctors = doctorRepository.findByNameContains(name);
		if (doctors.isEmpty())
			throw new DataNotFoundException("No Doctors Record Found with Name : " + name);
		else
			return doctors;
	}

	public List<Doctor> findBySpecialization(String specialization) {
		List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);
		if (doctors.isEmpty())
			throw new DataNotFoundException("No Doctors Record Found with Specialization in :" + specialization);
		else
			return doctors;
	}

	public DoctorTimeSlot getDoctorTimeSlotById(Long id) {
		return doctorTimeSlotRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("No Timeslot with Id: " + id));
	}

	public Doctor findByEmail(String email) {
		User user = userDao.findByEmail(email);
		return doctorRepository.findByUser(user).orElseThrow(() -> new DataNotFoundException("No Doctor Record Found"));
	}

	public List<Appointment> getAppointments(Doctor doctor) {
		List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
		if (appointments.isEmpty())
			throw new DataNotFoundException("No Appointments Scheduled yet");
		return appointments;
	}

	
}
