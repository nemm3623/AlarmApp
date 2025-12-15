package com.example.alarmapp.member.dto;

public record NaverProfile(
        String resultcode,
        String message,
        Response response
) {
    public record Response(
            String id,
            String email,
            String name,
            String gender,
            String birthyear,
            String mobile
    ) {}
}
