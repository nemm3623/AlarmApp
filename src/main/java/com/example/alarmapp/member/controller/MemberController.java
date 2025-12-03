package com.example.alarmapp.member.controller;

import com.example.alarmapp.member.dto.req.MemberJoinReqDTO;
import com.example.alarmapp.member.dto.req.MemberLoginReqDTO;
import com.example.alarmapp.member.dto.res.MemberJoinResDTO;
import com.example.alarmapp.member.dto.res.MemberLoginResDTO;
import com.example.alarmapp.member.service.MemberCommandService;
import com.example.alarmapp.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/register")
    public ResponseEntity<MemberJoinResDTO> signUp(@RequestBody MemberJoinReqDTO dto) {
        return ResponseEntity.ok(memberCommandService.registerMember(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResDTO> login(@RequestBody MemberLoginReqDTO dto) {
        return ResponseEntity.ok(memberQueryService.login(dto));
    }

}
