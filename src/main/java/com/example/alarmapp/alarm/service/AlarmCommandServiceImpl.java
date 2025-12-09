package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.CreateAlarmResDTO;
import com.example.alarmapp.alarm.dto.res.UpdateAlarmResDTO;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmCommandServiceImpl implements AlarmCommandService {

    private final AlarmRepository alarmRepository;


    @Override
    @Transactional
    public CreateAlarmResDTO createAlarm(CreateAlarmReqDTO dto) {

        System.out.println(dto.title() + " " + dto.repeat() + " " + dto.startTime() + " " +dto.priority() + " " + dto.led() + " " + dto.snoozeMinutes());

        Alarm alarm = Alarm.builder()

                .build();

        alarmRepository.save(alarm);

        return CreateAlarmResDTO.builder()
                .id(alarm.getId())
                .build();
    }

    @Override
    @Transactional
    public UpdateAlarmResDTO updateAlarm(Long id, UpdateAlarmReqDTO dto) {

        Alarm alarm = alarmRepository.getReferenceById(id);

        alarm.updateAlarm(dto);

        return UpdateAlarmResDTO.builder()
                .id(alarm.getId())
                .build();


    }

    @Override
    public void deleteAlarm(Long id) {
        alarmRepository.deleteById(id);
    }
}
