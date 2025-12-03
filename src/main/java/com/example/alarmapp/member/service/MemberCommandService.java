package com.example.alarmapp.member.service;


import com.example.alarmapp.member.dto.req.MemberJoinReqDTO;
import com.example.alarmapp.member.dto.res.MemberJoinResDTO;


public interface MemberCommandService {
    MemberJoinResDTO registerMember(MemberJoinReqDTO dto);
}
