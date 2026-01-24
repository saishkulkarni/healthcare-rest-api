package com.hms.healthcare.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hms.healthcare.dao.DoctorDao;
import com.hms.healthcare.dao.PatientDao;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.entity.MedicalHistory;
import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.mapper.UserMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final DoctorDao doctorDao;
	private final UserMapper userMapper;
	private final PatientDao patientDao;
	@Value("${razorpay.key}")
	private String key;
	@Value("${razorpay.secret}")
	private String secret;

	@Override
	public Map<String, Object> getDoctors(String name, String specialization, int size, int page, boolean desc,
			String sort) {
		List<Doctor> doctors = null;
		if (name == null && specialization == null)
			doctors = doctorDao.getAllDoctors(page, size, sort, desc);
		else {
			if (name != null && specialization != null)
				doctors = doctorDao.findByNameAndSpecialization(name, specialization);
			else {
				if (name != null)
					doctors = doctorDao.findByName(name);
				else
					doctors = doctorDao.findBySpecialization(specialization);
			}
		}
		return Map.of("message", "Doctors Records Found", "doctors", userMapper.toDoctorDtoList(doctors));
	}

	@Override
	public Map<String, Object> getDoctorsTimeSlot(Long id) {
		Doctor doctor = doctorDao.getByUserId(id);
		List<DoctorTimeSlot> timeSlots = doctorDao.getDoctorsAvailableTimeSlot(doctor);
		return Map.of("message", "Doctor AVailable at Below Time Slots", "timeSlots",
				userMapper.toTimeSlotDtoList(timeSlots));
	}

	@Override
	public Map<String, Object> bookAppointment(Long id, String email) {
		Patient patient = patientDao.findPatientByEmail(email);
		DoctorTimeSlot doctorTimeSlot = doctorDao.getDoctorTimeSlotById(id);
		Appointment appointment = new Appointment(null, doctorTimeSlot.getTimeSlot(), doctorTimeSlot.getDoctor(),
				patient, false, false, doctorTimeSlot.getFee(), null);
		patientDao.saveAppointment(appointment);
		doctorTimeSlot.setBooked(true);
		doctorDao.saveTimeSlot(doctorTimeSlot);
		return Map.of("message", "Appointment Booked Success", "appointment", userMapper.toAppointmentDto(appointment));
	}

	@Override
	public Map<String, Object> viewAppointments(String email) {
		Patient patient = patientDao.findPatientByEmail(email);
		List<Appointment> appointments = patientDao.getAppointments(patient);
		return Map.of("message", "Appoinments Found Success", "appointments",
				userMapper.toAppointmentDtoList(appointments));
	}

	@Override
	public Map<String, Object> createPayment(String email, Long patientId, Long id) {
		Patient patient = null;
		if (patientId == 0)
			patient = patientDao.findPatientByEmail(email);
		else {
			patient = patientDao.findById(id);
		}
		Appointment appointment = patientDao.getAppointmentFromId(id);
		double amount = appointment.getFee();
		Map<String, String> response = new HashMap<String, String>();
		response.put("key", key);
		response.put("currency", "INR");
		response.put("amount", amount * 100 + "");
		response.put("name", patient.getName());
		response.put("email", email);
		response.put("mobile", patient.getMobile() + "");

		try {
			RazorpayClient razorpayClient = new RazorpayClient(key, secret);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("currency", "INR");
			jsonObject.put("amount", amount * 100);

			Order order = razorpayClient.orders.create(jsonObject);
			String orderId = order.get("id");
			response.put("id", orderId);
		} catch (RazorpayException e) {
			e.printStackTrace();
			throw new RuntimeException("Something Went Wrong");
		}
		response.put("callbackUrl", "/patients/payment/confirm/" + id);
		return Map.of("message", "Payment Initiated Success", "payment", response);
	}

	@Override
	public Map<String, Object> confirmPayment(Long id, String razaorpay_payment_id) {
		Appointment appointment = patientDao.getAppointmentFromId(id);
		appointment.setPaymentStatus(true);
		appointment.setPaymentId(razaorpay_payment_id);
		patientDao.saveAppointment(appointment);
		return Map.of("message", "Payment COnfirmed", "appointment", userMapper.toAppointmentDto(appointment));
	}

	@Override
	public Map<String, Object> viewHistory(String email) {
		Patient patient = patientDao.findPatientByEmail(email);
		List<MedicalHistory> histories = patientDao.getMedicalHistory(patient);
		return Map.of("message", "Details Found", "medical-history", histories);
	}

}
