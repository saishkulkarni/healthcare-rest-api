package com.hms.healthcare.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DoctorTimeSlotDto {
	private String name;
	private String specialization;
	private LocalDateTime timeSlot;
}
