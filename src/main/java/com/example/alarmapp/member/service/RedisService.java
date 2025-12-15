package com.example.alarmapp.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(Long memberId, String refreshToken, long ttlMillis) {
        redisTemplate.opsForValue().set(
                "RT:" + memberId,
                refreshToken,
                ttlMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public String getRefreshToken(Long memberId) {
        return redisTemplate.opsForValue().get("RT:" + memberId);
    }

    public void deleteRefreshToken(Long memberId) {
        redisTemplate.delete("RT:" + memberId);
    }
}
