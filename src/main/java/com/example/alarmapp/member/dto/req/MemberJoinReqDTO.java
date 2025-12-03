package com.example.alarmapp.member.dto.req;

import lombok.Builder;

@Builder
public record MemberJoinReqDTO(

        String email,
        String password

) {
}
