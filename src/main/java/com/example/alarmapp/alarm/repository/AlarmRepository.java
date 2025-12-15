package com.example.alarmapp.alarm.repository;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.alarm.enums.AlarmType;
import com.example.alarmapp.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("SELECT a FROM Alarm a WHERE a.member = :member")
    List<Alarm> findByMember(@Param("member") Member member);



    @Query(""" 
            SELECT a FROM Alarm a
            WHERE a.type = :type AND a.startTime <= :now
            AND a.startTime > :past
            """ )
            List<Alarm> findAlarmsToTrigger(
            @Param("type") AlarmType type,
            @Param("now") LocalTime now,
            @Param("past") LocalTime past
    );


}
