package com.example.alarmapp.alarm.dto.req;

import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record CreateAlarmReqDTO (

    String title,
    AlarmType type, // LOCAL, PUSH
    Repeat repeat, // DAILY, WEEKLY, MONTHLY
    Weekday weekdays, // MON,TUE,... (선택 사항)

    LocalTime startTime, // 선택 사항
    LocalTime endTime,   // 선택 사항

    int priority,
    boolean sound,
    boolean vibration,
    boolean led,
    int snoozeMinutes
){
}
