package com.example.alarmapp.member.domain;

import com.example.alarmapp.alarm.domain.Alarm;
import com.example.alarmapp.member.enums.Role;
import com.example.alarmapp.member.enums.SocialProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String gender;
    private String birthyear;

    // 네이버 id, 카카오 id, 구글 sub 값 → 문자열!
    @Column(unique = true, nullable = false, name = "provider_id")
    private String providerId;

    @Enumerated(EnumType.STRING)
    private SocialProvider provider;

    // unique 절대 X
    @Column(name = "fcm_token")
    @Builder.Default
    private String fcmToken = null;

    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    @Transactional
    public void updateFCMToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
