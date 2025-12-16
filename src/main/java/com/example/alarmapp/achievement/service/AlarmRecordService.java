package com.example.alarmapp.achievement.service;

import com.example.alarmapp.achievement.domain.AlarmRecord;
import com.example.alarmapp.achievement.enums.AlarmRecordStatus;
import com.example.alarmapp.achievement.repository.AlarmRecordRepository;
import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AlarmRecordService {

    private final AlarmRecordRepository recordRepository;
    private final AlarmRepository alarmRepository;

    public void record(Member member, Long alarmId, AlarmRecordStatus status) {

        Alarm alarm = alarmRepository.findById(alarmId).orElse(null);

        AlarmRecord record = AlarmRecord.builder()
                .member(member)
                .alarm(alarm)
                .status(status)
                .date(LocalDate.now())
                .build();

        recordRepository.save(record);
    }
}
