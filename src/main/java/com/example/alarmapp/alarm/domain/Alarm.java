package com.example.alarmapp.alarm.domain;

import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.alarm.enums.Repeat;
import com.example.alarmapp.alarm.enums.Weekday;
import com.example.alarmapp.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alarms")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private AlarmType type; // LOCAL, PUSH

    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_type")
    private Repeat repeat;  // DAILY, WEEKLY, MONTHLY, ONCE

    /* ================== 반복 조건 ================== */

    // WEEKLY
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "alarm_weekdays", joinColumns = @JoinColumn(name = "alarm_id"))
    @Enumerated(EnumType.STRING)
    private List<Weekday> weekdays;

    // MONTHLY
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "alarm_monthdays", joinColumns = @JoinColumn(name = "alarm_id"))
    private List<Integer> monthdays;


    private LocalTime startTime;
    private LocalTime endTime;


    private int priority;
    private boolean sound;
    private boolean vibration;
    private boolean led;
    private int snoozeMinutes;



    private LocalDate lastTriggeredDate;



    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public boolean isAlreadyTriggeredToday() {
        return lastTriggeredDate != null &&
                lastTriggeredDate.equals(LocalDate.now());
    }

    public void markTriggeredToday() {
        this.lastTriggeredDate = LocalDate.now();
    }

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

    public boolean isNoneAlarm() {
        return this.repeat == Repeat.NONE;
    }

}
