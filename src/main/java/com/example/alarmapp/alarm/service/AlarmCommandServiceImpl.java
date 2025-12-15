package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.dto.res.CreateAlarmResDTO;
import com.example.alarmapp.alarm.dto.res.UpdateAlarmResDTO;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmCommandServiceImpl implements AlarmCommandService {

    private final AlarmRepository alarmRepository;


    @Override
    @Transactional
    public AlarmResDTO createAlarm(CreateAlarmReqDTO dto, Member member) {

        Alarm alarm = Alarm.builder()
                .title(dto.title())
                .type(dto.type())
                .repeat(dto.repeat())
                .weekdays(dto.weekdays())
                .monthdays(dto.monthdays())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .priority(dto.priority())
                .sound(dto.sound())
                .vibration(dto.vibration())
                .led(dto.led())
                .snoozeMinutes(dto.snoozeMinutes())
                .member(member)
                .build();

        alarmRepository.save(alarm);

        return AlarmResDTO.from(alarm);
    }

    @Override
    @Transactional
    public AlarmResDTO updateAlarm(Long id, UpdateAlarmReqDTO dto) {

        Alarm alarm = alarmRepository.getReferenceById(id);

        alarm.updateAlarm(dto);

        return AlarmResDTO.from(alarm);

    }

    @Override
    public void deleteAlarm(Long id) {
        alarmRepository.deleteById(id);
    }
}
