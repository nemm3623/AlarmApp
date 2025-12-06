package com.example.alarmapp.alarm.domain;

import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private AlarmType type; // LOCAL, PUSH

    @Column(name = "repeat_type")
    private Repeat repeat;

    private Weekday weekdays; // MON,TUE,... (선택 사항)

    private LocalTime startTime; // 선택 사항
    private LocalTime endTime;   // 선택 사항

    private int priority;
    private boolean sound;
    private boolean vibration;
    private boolean led;
    private int snoozeMinutes;

}
