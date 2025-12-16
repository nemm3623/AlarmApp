package com.example.alarmapp.achievement.domain;

import com.example.alarmapp.achievement.enums.AlarmRecordStatus;
import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "alarm_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AlarmRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 회원의 기록인지 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /** 어떤 알람에 대한 기록인지 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    /** 이 기록이 찍힌 날짜 (로컬 날짜 기준) */
    private LocalDate date;

    /** 완료 / 스누즈 / 취소 */
    @Enumerated(EnumType.STRING)
    private AlarmRecordStatus status;

    // ---- 편의 메서드들 ----
    public boolean isComplete() {
        return status == AlarmRecordStatus.COMPLETE;
    }

    public boolean isSnooze() {
        return status == AlarmRecordStatus.SNOOZE;
    }

    public boolean isCancel() {
        return status == AlarmRecordStatus.CANCEL;
    }

    // 기록 생성용 팩토리 메서드 (있으면 쓰기 편함)
    public static AlarmRecord of(Member member, Alarm alarm, LocalDate date, AlarmRecordStatus status) {
        return AlarmRecord.builder()
                .member(member)
                .alarm(alarm)
                .date(date)
                .status(status)
                .build();
    }
}
