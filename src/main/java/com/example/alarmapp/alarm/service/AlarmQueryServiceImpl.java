package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
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
    public List<AlarmResDTO> allAlarms(Member member) {
        List<Alarm> alarms = alarmRepository.findByMember(member);
        List<AlarmResDTO> alarmResDTOs = new ArrayList<>();
        for (Alarm alarm : alarms) {
            alarmResDTOs.add(AlarmResDTO.from(alarm));
        }
        return alarmResDTOs;
    }

    @Override
    public AlarmResDTO getAlarm(Long id) {

        Alarm alarm = alarmRepository.findById(id).orElse(null);

        if (alarm == null)
            throw new IllegalArgumentException("Alarm not found");

        return AlarmResDTO.from(alarm);

    }
}
