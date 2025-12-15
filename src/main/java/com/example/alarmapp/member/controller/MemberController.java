package com.example.alarmapp.member.controller;

import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.dto.req.NaverLoginReqDTO;
import com.example.alarmapp.member.dto.req.UpdateTokenReq;
import com.example.alarmapp.member.dto.res.AuthResponse;
import com.example.alarmapp.member.service.MemberCommandService;
import com.example.alarmapp.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin("*")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;


    @PostMapping("/auth/naver")
    public ResponseEntity<AuthResponse> login(@RequestBody NaverLoginReqDTO dto) {

        return ResponseEntity.ok(memberQueryService.handleNaverLogin(
                memberQueryService.validateNaverToken(dto)));
    }

    @PostMapping("/token")
    public ResponseEntity<Void> updateToken(Authentication authentication, @RequestBody UpdateTokenReq req) {
        Member member = (Member) authentication.getPrincipal();

        memberCommandService.updateToken(member, req.token());
        return ResponseEntity.noContent().build();
    }



}
