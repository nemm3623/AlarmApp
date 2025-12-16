package com.example.alarmapp.achievement.service;

import com.example.alarmapp.achievement.domain.AlarmRecord;
import com.example.alarmapp.achievement.dto.res.*;
import com.example.alarmapp.achievement.repository.AlarmRecordRepository;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AlarmRecordRepository repo;

    public DailyAchievementResponse getToday(Member member) {
        LocalDate today = LocalDate.now();
        List<AlarmRecord> records = repo.findByMemberAndDate(member, today);

        int complete = (int) records.stream().filter(AlarmRecord::isComplete).count();
        int snooze = (int) records.stream().filter(AlarmRecord::isSnooze).count();
        int cancel = (int) records.stream().filter(AlarmRecord::isCancel).count();

        int total = complete + snooze + cancel;
        int percent = total == 0 ? 0 : (int) ((complete * 100.0 + snooze * 50.0) / total);

        return new DailyAchievementResponse(
                today.toString(),
                percent,
                complete,
                snooze,
                cancel
        );
    }

    public WeeklyAchievementResponse getWeekly(Member member) {

        LocalDate now = LocalDate.now();
        LocalDate monday = now.with(DayOfWeek.MONDAY);
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);

        List<AlarmRecord> records = repo.findWeekly(member, monday, sunday);

        Map<LocalDate, List<AlarmRecord>> grouped =
                records.stream().collect(Collectors.groupingBy(AlarmRecord::getDate));

        List<DailyAchievementItem> list = new ArrayList<>();

        int weekTotalComplete = 0;
        int weekTotalSnooze = 0;
        int weekTotalCancel = 0;
        int weekTotal = 0;

        for (LocalDate date = monday; !date.isAfter(sunday); date = date.plusDays(1)) {
            List<AlarmRecord> dayRecords = grouped.getOrDefault(date, List.of());

            int complete = (int) dayRecords.stream().filter(AlarmRecord::isComplete).count();
            int snooze = (int) dayRecords.stream().filter(AlarmRecord::isSnooze).count();
            int cancel = (int) dayRecords.stream().filter(AlarmRecord::isCancel).count();

            int total = complete + snooze + cancel;
            long p = total == 0 ? 0 : (int) ((complete * 100.0 + snooze * 50.0) / total);

            list.add(new DailyAchievementItem(date.toString(), p));

            weekTotalComplete += complete;
            weekTotalSnooze += snooze;
            weekTotalCancel += cancel;
            weekTotal += total;
        }

        long weeklyPercent = weekTotal == 0 ? 0 : (int) ((weekTotalComplete * 100.0 + weekTotalSnooze * 50.0) / weekTotal);

        WeeklyAchievementResponse  response = new WeeklyAchievementResponse(
                weeklyPercent,
                monday.toString(),
                sunday.toString(),
                weekTotalComplete,
                weekTotalSnooze,
                weekTotalCancel,
                list);

        System.out.println(response.weekStart());

        return response;

    }

    /**
     * 이번 달 성취도
     */
    public MonthlyAchievementResponse getMonthly(Member member) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate start = yearMonth.atDay(1);
        LocalDate end   = yearMonth.atEndOfMonth();

        List<AlarmRecord> records = repo.findMonthly(member, start, end);

        Map<LocalDate, List<AlarmRecord>> grouped =
                records.stream().collect(Collectors.groupingBy(AlarmRecord::getDate));

        List<DailyAchievementItem> list = new ArrayList<>();

        int totalComplete = 0;
        int totalSnooze = 0;
        int totalCancel = 0;
        int totalCount = 0;

        int monthLength = now.lengthOfMonth();

        for (int day = 1; day <= monthLength; day++) {
            LocalDate date = LocalDate.of(year, month, day);

            List<AlarmRecord> dayRecords = grouped.getOrDefault(date, List.of());

            int complete = (int) dayRecords.stream().filter(AlarmRecord::isComplete).count();
            int snooze = (int) dayRecords.stream().filter(AlarmRecord::isSnooze).count();
            int cancel = (int) dayRecords.stream().filter(AlarmRecord::isCancel).count();

            int total = complete + snooze + cancel;
            long p = total == 0 ? 0 : (int) ((complete * 100.0 + snooze * 50.0) / total);

            list.add(new DailyAchievementItem(date.toString(), p));

            totalComplete += complete;
            totalSnooze += snooze;
            totalCancel += cancel;
            totalCount += total;
        }

        long monthlyPercent = totalCount == 0 ? 0 : (int) ((totalComplete * 100.0 + totalSnooze * 50.0) / totalCount);

        return new MonthlyAchievementResponse(
                monthlyPercent,
                now.getMonth().toString(),
                totalComplete,
                totalSnooze,
                totalCancel,
                list
        );
    }
}
