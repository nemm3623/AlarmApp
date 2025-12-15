package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.member.domain.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlarmQueryService {
    List<AlarmResDTO> allAlarms(Member member);

    AlarmResDTO getAlarm(Long id);
}
