package com.example.alarmapp.member.service;

import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.dto.req.MemberLoginReqDTO;
import com.example.alarmapp.member.dto.res.MemberLoginResDTO;
import com.example.alarmapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberLoginResDTO login(MemberLoginReqDTO dto) {

        System.out.println(dto.email());

        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));


        if (!member.getPassword().equals(dto.password())) {
            System.out.println(member.getPassword() + " " + dto.password());
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        return MemberLoginResDTO.builder()
                .id(member.getId())
                .build();
    }
}
