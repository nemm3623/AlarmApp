package com.example.alarmapp.global.jwt;

public record TokenPair(
        String access,
        String refresh
) {
}
