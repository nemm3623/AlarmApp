package com.example.alarmapp.member.service;


import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.dto.req.MemberJoinReqDTO;
import com.example.alarmapp.member.dto.res.MemberJoinResDTO;
import com.example.alarmapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    MemberJoinResDTO registerMember(MemberJoinReqDTO dto){

        if (memberRepository.existsByEmail(dto.email()))
            throw new RuntimeException("Email already exists");

        Member member = Member.builder()
                .email(dto.email())
                .password(dto.password())
                .build();

        memberRepository.save(member);

        MemberJoinResDTO joinResDTO = MemberJoinResDTO.builder()
                .id(member.getId())
                .build();

        return joinResDTO;
    };
}
