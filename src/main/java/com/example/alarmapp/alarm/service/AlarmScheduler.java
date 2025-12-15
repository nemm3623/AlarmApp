package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmScheduler {

    private final AlarmRepository alarmRepository;
    private final FcmService fcmService;

    @Transactional
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkAndSendPushAlarms() {

        LocalTime now = LocalTime.now();
        LocalTime past = now.minusMinutes(1);

        List<Alarm> alarms = alarmRepository.findAlarmsToTrigger(AlarmType.PUSH, now, past);

        if (alarms.isEmpty()) return;

        for (Alarm alarm : alarms) {
            fcmService.sendAlarmPush(
                alarm.getMember(), 
                alarm.getId(), 
                alarm.getTitle(), 
                alarm.getSnoozeMinutes()

            );
        }
    }
}
