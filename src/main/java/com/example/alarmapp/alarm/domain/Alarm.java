package com.example.alarmapp.alarm.domain;

import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="alarms")
@Builder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private AlarmType type; // LOCAL, PUSH

    @Column(name = "repeat_type")
    private Repeat repeat;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Weekday> weekdays;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> monthdays;

    private LocalTime startTime; // 선택 사항
    private LocalTime endTime;   // 선택 사항

    private int priority;   // 알람 중요 수치
    private boolean sound;
    private boolean vibration;
    private boolean led;
    private int snoozeMinutes;


    public void updateAlarm(UpdateAlarmReqDTO dto) {

        this.title = dto.title();
        this.type = dto.type();
        this.repeat = dto.repeat();
        this.weekdays = dto.weekdays();
        this.monthdays = dto.monthdays();
        this.startTime = dto.startTime();
        this.endTime = dto.endTime();
        this.priority = dto.priority();
        this.sound = dto.sound();
        this.vibration = dto.vibration();
        this.led = dto.led();
        this.snoozeMinutes = dto.snoozeMinutes();
    }
}
