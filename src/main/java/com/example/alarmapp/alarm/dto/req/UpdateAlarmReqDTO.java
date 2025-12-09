package com.example.alarmapp.alarm.dto.req;

import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;

import java.time.LocalTime;
import java.util.List;

public record UpdateAlarmReqDTO(


        String title,
        AlarmType type,
        Repeat repeat,
        List<Weekday> weekdays,
        List<Integer> monthdays,

        LocalTime startTime,
        LocalTime endTime,

        Integer priority,

        Boolean sound,
        Boolean vibration,
        Boolean led,

        Integer snoozeMinutes // 이것도 null 가능성이 있으니 Integer 권장

) {
}
