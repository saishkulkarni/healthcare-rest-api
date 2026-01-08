package com.hms.healthcare.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hms.healthcare.dto.DoctorDto;
import com.hms.healthcare.entity.Doctor;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
	
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "experience", source = "experienceYears")
	@Mapping(target = "email", expression = "java(doctor.getUser().getEmail())")
	DoctorDto toDoctorDto(Doctor doctor);

	List<DoctorDto> toDoctorDtoList(List<Doctor> doctors);
}
