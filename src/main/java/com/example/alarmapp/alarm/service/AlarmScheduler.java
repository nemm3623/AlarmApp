package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Weekday;
import com.example.alarmapp.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmScheduler {

    private final AlarmRepository alarmRepository;
    private final FcmService fcmService;

    @Transactional
    @Scheduled(fixedRate = 60000) // 1분마다
    public void checkAndSendPushAlarms() {

        LocalTime now = LocalTime.now();
        LocalTime past = now.minusMinutes(1);

        // 1️⃣ 시간 기준으로 울릴 후보만 조회
        List<Alarm> alarms =
                alarmRepository.findAlarmsByTimeRange(AlarmType.PUSH, now, past);

        for (Alarm alarm : alarms) {

            // 2️⃣ 오늘 울릴 조건인지 확인
            if (!shouldTriggerToday(alarm)) continue;

            // 3️⃣ 반복 알람 중복 방지
            if (!alarm.isNoneAlarm() && alarm.isAlreadyTriggeredToday()) continue;

            // 🔔 알림 전송
            fcmService.sendAlarmPush(
                    alarm.getMember(),
                    alarm.getId(),
                    alarm.getTitle(),
                    alarm.getSnoozeMinutes()
            );

            if (alarm.isNoneAlarm()) {
                alarmRepository.delete(alarm);
                continue;
            }

            // 🔒 반복 알람 → 오늘 울림 기록
            alarm.markTriggeredToday();
        }
    }

    /* ================== 오늘 울릴 알람인지 판단 ================== */
    private boolean shouldTriggerToday(Alarm alarm) {

        LocalDate today = LocalDate.now();
        DayOfWeek todayWeek = today.getDayOfWeek();

        return switch (alarm.getRepeat()) {

            case DAILY -> true;

            case WEEKLY ->
                    alarm.getWeekdays() != null &&
                            alarm.getWeekdays().contains(Weekday.from(todayWeek));

            case MONTHLY ->
                    alarm.getMonthdays() != null &&
                            alarm.getMonthdays().contains(today.getDayOfMonth());

            case NONE ->
                    true; // ⭐ 날짜 조건 없음 (시간 윈도우에 걸리면 울림)
        };
    }
}
