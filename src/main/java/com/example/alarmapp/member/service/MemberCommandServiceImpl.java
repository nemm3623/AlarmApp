package com.example.alarmapp.member.service;


import com.example.alarmapp.member.domain.Member;
import com.example.alarmapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void updateToken(Member member, String token) {
        Member memberToUpdate = memberRepository.findById(member.getId()).orElseThrow();
        memberToUpdate.updateFCMToken(token);
    }

    ;
}
