package com.hms.healthcare.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.dto.PatientDto;
import com.hms.healthcare.dto.ReceptionistDto;
import com.hms.healthcare.dto.UserResponseDto;
import com.hms.healthcare.entity.Doctor;
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
	DoctorDto toDoctorDto(Doctor doctor);

	List<DoctorDto> toDoctorDtoList(List<Doctor> doctors);

	@Mapping(target = "password", expression = "java(\"**********\")")
	@Mapping(target = "email", expression = "java(receptionist.getUser().getEmail())")
	@Mapping(target = "userId", expression = "java(receptionist.getUser().getId())")
	ReceptionistDto toReceptionistDto(Receptionist receptionist);

	List<ReceptionistDto> toReceptionistDtoList(List<Receptionist> receptionists);
	
	@Mapping(target = "password", expression = "java(\"**********\")")
	@Mapping(target = "email", expression = "java(patient.getUser().getEmail())")
	@Mapping(target = "userId", expression = "java(patient.getUser().getId())")
	PatientDto toPatientDto(Patient patient);

	List<PatientDto> toPatientsDtoList(List<Patient> patients);

}