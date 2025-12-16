package com.example.alarmapp.member.service;

import com.example.alarmapp.global.jwt.JwtTokenProvider;
import com.example.alarmapp.global.jwt.TokenPair;
import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.dto.req.NaverLoginReqDTO;
import com.example.alarmapp.member.dto.NaverProfile;
import com.example.alarmapp.member.dto.res.AuthResponse;
import com.example.alarmapp.member.enums.Role;
import com.example.alarmapp.member.enums.SocialProvider;
import com.example.alarmapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtProvider;
    private final RedisService redisService;

    public NaverProfile validateNaverToken(NaverLoginReqDTO dto) {

        System.out.println(dto.accessToken());

        WebClient client = WebClient.create("https://openapi.naver.com/v1/nid/me");

        NaverProfile profile = client.get()
                .header("Authorization", "Bearer " + dto.accessToken())
                .retrieve()
                .bodyToMono(NaverProfile.class)
                .block();

        System.out.println("네이버 API 응답: " + profile);

        return profile;
    }

    public AuthResponse handleNaverLogin(NaverProfile profile) {

        String email = profile.response().email();
        String name = profile.response().name();
        String gender = profile.response().gender();
        String naver_id = profile.response().id();
        String birth = profile.response().birthyear();

        // 1. DB 조회
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 2. 회원이 없음 → 회원가입 진행
                    Member newMember = Member.builder()
                            .email(email)
                            .name(name)
                            .gender(gender)
                            .birthyear(birth)
                            .providerId(naver_id)
                            .provider(SocialProvider.NAVER)
                            .role(Role.ROLE_MEMBER)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 3. JWT 발급
        TokenPair tokens = jwtProvider.generateTokens(member);

        // 4. Refresh Token 저장 (Redis)
        redisService.saveRefreshToken(member.getId(), tokens.refresh(),1000L * 60 * 60 * 24 * 14);

        System.out.println(tokens.access());

        // 5. 프론트로 응답
        return AuthResponse.builder()
                .id(member.getId())
                .accessToken(tokens.access())
                .refreshToken(tokens.refresh())
                .build();
    }

}
