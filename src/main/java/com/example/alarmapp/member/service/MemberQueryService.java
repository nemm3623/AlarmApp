package com.example.alarmapp.member.service;

import com.example.alarmapp.member.dto.req.MemberLoginReqDTO;
import com.example.alarmapp.member.dto.res.MemberLoginResDTO;

public interface MemberQueryService {
    MemberLoginResDTO login(MemberLoginReqDTO memberLoginReqDTO);
}
