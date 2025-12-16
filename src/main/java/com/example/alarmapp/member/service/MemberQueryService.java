package com.example.alarmapp.member.service;

import com.example.alarmapp.member.dto.req.NaverLoginReqDTO;
import com.example.alarmapp.member.dto.NaverProfile;
import com.example.alarmapp.member.dto.res.AuthResponse;

public interface MemberQueryService {
    NaverProfile validateNaverToken(NaverLoginReqDTO dto);
    AuthResponse handleNaverLogin(NaverProfile profile);
}
