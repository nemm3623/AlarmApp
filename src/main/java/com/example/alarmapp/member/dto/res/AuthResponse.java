package com.example.alarmapp.member.dto.res;

import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String accessToken,
        String refreshToken
) {
}
