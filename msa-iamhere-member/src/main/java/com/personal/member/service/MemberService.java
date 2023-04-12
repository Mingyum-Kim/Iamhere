package com.personal.member.service;

import com.personal.member.domain.Member;
import com.personal.member.dto.MemberDTO;
import com.personal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Long insertMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.setMail(memberDTO.getMail());
        member.setPassword(memberDTO.getPassword());
        member.setBirth(memberDTO.getBirth());

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
