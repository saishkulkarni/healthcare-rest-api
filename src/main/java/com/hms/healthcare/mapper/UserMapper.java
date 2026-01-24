package com.hms.healthcare.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hms.healthcare.dto.AppointmentDto;
import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.DoctorTimeSlotDto;
import com.hms.healthcare.dto.PatientDto;
import com.hms.healthcare.dto.ReceptionistDto;
import com.hms.healthcare.dto.TimeSlotRequestDto;
import com.hms.healthcare.dto.UserResponseDto;
import com.hms.healthcare.entity.Appointment;
import com.hms.healthcare.entity.Doctor;
import com.hms.healthcare.entity.DoctorTimeSlot;
import com.hms.healthcare.entity.Patient;
import com.hms.healthcare.entity.Receptionist;
import com.hms.healthcare.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto toUserResponseDto(User user);

	@Mapping(target = "password", expression = "java(\"**********\")")
	@Mapping(target = "experience", source = "experienceYears")
	@Mapping(target = "email", expression = "java(doctor.getUser().getEmail())")
	@Mapping(target = "userId", expression = "java(doctor.getUser().getId())")
	@Mapping(target = "status", expression = "java(doctor.getUser().getIsActive())")
	DoctorDto toDoctorDto(Doctor doctor);

	List<DoctorDto> toDoctorDtoList(List<Doctor> doctors);

	@Mapping(target = "password", expression = "java(\"**********\")")
	@Mapping(target = "email", expression = "java(receptionist.getUser().getEmail())")
	@Mapping(target = "userId", expression = "java(receptionist.getUser().getId())")
	@Mapping(target = "status", expression = "java(receptionist.getUser().getIsActive())")
	ReceptionistDto toReceptionistDto(Receptionist receptionist);

	List<ReceptionistDto> toReceptionistDtoList(List<Receptionist> receptionists);

	@Mapping(target = "password", expression = "java(\"**********\")")
	@Mapping(target = "email", expression = "java(patient.getUser().getEmail())")
	@Mapping(target = "userId", expression = "java(patient.getUser().getId())")
	PatientDto toPatientDto(Patient patient);

	List<PatientDto> toPatientsDtoList(List<Patient> patients);

	@Mapping(target = "name", expression = "java(timeSlot.getDoctor().getName())")
	@Mapping(target = "timeSlot", expression = "java(timeSlot.getTimeSlot())")
	@Mapping(target = "specialization", expression = "java(timeSlot.getDoctor().getSpecialization())")
	DoctorTimeSlotDto toTimeSlotDto(DoctorTimeSlot timeSlot);

	List<DoctorTimeSlotDto> toTimeSlotDtoList(List<DoctorTimeSlot> timeSlots);

	@Mapping(target = "booked", ignore = true)
	@Mapping(target = "timeSlot", expression = "java(requestDto.getTimeSlot())")
	@Mapping(target = "doctor", source = "doctor")
	@Mapping(target = "id", ignore = true)
	DoctorTimeSlot toDotorTimeSlot(TimeSlotRequestDto requestDto, Doctor doctor);

	@Mapping(target = "appointmentId", source = "id")
	@Mapping(target = "patientName", expression = "java(appointment.getPatient().getName())")
	@Mapping(target = "doctorName", expression = "java(appointment.getDoctor().getName())")
	@Mapping(target = "paymentStatus", expression = "java(appointment.isPaymentStatus()?\"Done\":\"Not Done\")")
	@Mapping(target = "doctorMobile", expression = "java(appointment.getDoctor().getPhoneNumber())")
	@Mapping(target = "patientMobile", expression = "java(appointment.getPatient().getMobile())")
	AppointmentDto toAppointmentDto(Appointment appointment);

	List<AppointmentDto> toAppointmentDtoList(List<Appointment> appointments);

}