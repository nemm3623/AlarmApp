package com.example.alarmapp.alarm.dto.res;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;


import java.time.LocalTime;
import java.util.List;


public record AlarmResDTO (
        Long id,

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

){
    public static AlarmResDTO from(Alarm alarm) {
    return new AlarmResDTO(
            alarm.getId(),
            alarm.getTitle(),
            alarm.getType(),
            alarm.getRepeat(),
            alarm.getWeekdays(),
            alarm.getMonthdays(),
            alarm.getStartTime() != null ? alarm.getStartTime() : null,
            alarm.getEndTime() != null ? alarm.getEndTime() : null,
            alarm.getPriority(),
            alarm.isSound(),
            alarm.isVibration(),
            alarm.isLed(),
            alarm.getSnoozeMinutes()
    );
}

}
