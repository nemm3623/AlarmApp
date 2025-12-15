package com.example.alarmapp.global.jwt;

import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.repository.MemberRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secret;

    private static final long ACCESS_TOKEN_EXP = 1000L * 60 * 30;      // 30분
    private static final long REFRESH_TOKEN_EXP = 1000L * 60 * 60 * 24 * 7; // 7일

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenPair generateTokens(Member member) {
        String access = createAccessToken(member);
        String refresh = createRefreshToken(member);
        return new TokenPair(access, refresh);
    }

    public String createAccessToken(Member member) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXP);

        return Jwts.builder()
                .setSubject(member.getId().toString())          // 🔑 memberId를 sub 에 넣자
                .claim("email", member.getEmail())
                .claim("provider", member.getProvider().name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Member member) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXP);

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {

        // 🔥 1. JWT에서 memberId 추출
        Long memberId = getMemberId(token);

        // 🔥 2. DB에서 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        // 🔥 3. ROLE 부여 (너는 현재 ROLE_USER 하나만 쓰고 있음)
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // 🔥 4. Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                member,        // principal
                null,          // credentials(비밀번호 없음)
                authorities    // 권한
        );
    }


    public Long getMemberId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

}
