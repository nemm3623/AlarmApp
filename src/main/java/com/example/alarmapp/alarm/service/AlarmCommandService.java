package com.example.alarmapp.alarm.service;

import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.CreateAlarmResDTO;
import com.example.alarmapp.alarm.dto.res.UpdateAlarmResDTO;


public interface AlarmCommandService {

    CreateAlarmResDTO createAlarm(CreateAlarmReqDTO dto);

    UpdateAlarmResDTO updateAlarm(Long id,UpdateAlarmReqDTO dto);

    void deleteAlarm(Long id);
}
