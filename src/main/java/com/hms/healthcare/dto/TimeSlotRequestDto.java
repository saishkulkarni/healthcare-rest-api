package com.hms.healthcare.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TimeSlotRequestDto {
	private Long userId;
	private LocalDateTime timeSlot;
	private Double fee;
}
