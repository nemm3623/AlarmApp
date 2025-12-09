package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlarmQueryService {
    List<AlarmResDTO> allAlarms();

    AlarmResDTO getAlarm(Long id);
}
