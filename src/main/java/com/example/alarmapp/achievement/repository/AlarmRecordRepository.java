package com.example.alarmapp.achievement.repository;

import com.example.alarmapp.achievement.domain.AlarmRecord;
import com.example.alarmapp.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AlarmRecordRepository extends JpaRepository<AlarmRecord, Long> {

    List<AlarmRecord> findByMemberAndDate(Member member, LocalDate date);

    @Query("""
    SELECT r
    FROM AlarmRecord r
    WHERE r.member = :member
      AND r.date BETWEEN :start AND :end
    """)
    List<AlarmRecord> findWeekly(
            @Param("member") Member member,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
    SELECT r
    FROM AlarmRecord r
    WHERE r.member = :member
      AND r.date BETWEEN :start AND :end
""")
    List<AlarmRecord> findMonthly(
            @Param("member") Member member,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

}
