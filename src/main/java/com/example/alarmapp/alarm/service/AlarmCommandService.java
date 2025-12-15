package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.dto.res.CreateAlarmResDTO;
import com.example.alarmapp.alarm.dto.res.UpdateAlarmResDTO;
import com.example.alarmapp.member.domain.Member;


public interface AlarmCommandService {

    AlarmResDTO createAlarm(CreateAlarmReqDTO dto, Member member);

    AlarmResDTO updateAlarm(Long id,UpdateAlarmReqDTO dto);

    void deleteAlarm(Long id);
}
