package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmQueryServiceImpl implements AlarmQueryService {

    private final AlarmRepository alarmRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AlarmResDTO> allAlarms() {
        List<Alarm> alarms = alarmRepository.findAll();
        List<AlarmResDTO> alarmResDTOs = new ArrayList<>();
        for (Alarm alarm : alarms) {
            alarmResDTOs.add(AlarmResDTO.builder()
                    .id(alarm.getId())
                    .title(alarm.getTitle())
                    .type(alarm.getType())
                    .repeat(alarm.getRepeat())
                    .weekdays(alarm.getWeekdays())
                    .startTime(alarm.getStartTime())
                    .monthdays(alarm.getMonthdays())
                    .endTime(alarm.getEndTime())
                    .priority(alarm.getPriority())
                    .sound(alarm.isSound())
                    .vibration(alarm.isVibration())
                    .led(alarm.isLed())
                    .snoozeMinutes(alarm.getSnoozeMinutes())
                    .build());
        }
        return alarmResDTOs;
    }

    @Override
    public AlarmResDTO getAlarm(Long id) {

        Alarm alarm = alarmRepository.findById(id).orElse(null);

        if (alarm == null)
            throw new IllegalArgumentException("Alarm not found");

        return AlarmResDTO.builder()
                .id(alarm.getId())
                .title(alarm.getTitle())
                .type(alarm.getType())
                .repeat(alarm.getRepeat())
                .weekdays(alarm.getWeekdays())
                .startTime(alarm.getStartTime())
                .monthdays(alarm.getMonthdays())
                .endTime(alarm.getEndTime())
                .priority(alarm.getPriority())
                .sound(alarm.isSound())
                .vibration(alarm.isVibration())
                .led(alarm.isLed())
                .snoozeMinutes(alarm.getSnoozeMinutes())
                .build();

    }
}
