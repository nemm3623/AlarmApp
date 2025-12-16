package com.example.alarmapp.achievement.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AchievementLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 회원의 로그인지 */
    private Long memberId;

    /** 날짜별 성취 기록 */
    private LocalDate date;

    /** 완료 횟수 */
    private int completeCount;

    /** 스누즈 횟수 */
    private int snoozeCount;

    /** 취소(포기) 횟수 */
    private int cancelCount;

    /** 오늘 성취도 퍼센트 = (완료 / 전체) * 100 */
    public int getPercent() {
        int total = completeCount + snoozeCount + cancelCount;
        if (total == 0) return 0;
        return (int) ((completeCount * 100.0) / total);
    }

    /** 완료 증가 */
    public void increaseComplete() {
        this.completeCount++;
    }

    /** 스누즈 증가 */
    public void increaseSnooze() {
        this.snoozeCount++;
    }

    /** 취소 증가 */
    public void increaseCancel() {
        this.cancelCount++;
    }
}
