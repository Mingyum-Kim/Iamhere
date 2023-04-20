package com.personal.member.service;

import com.personal.member.domain.Member;
import com.personal.member.dto.MemberDTO;
import com.personal.member.exception.AppException;
import com.personal.member.exception.ErrorCode;
import com.personal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public Long insertMember(MemberDTO memberDTO) throws Exception {
        if (this.isEmailExists(memberDTO.getMail())) {
            throw new AppException(ErrorCode.MAIL_DUPLICATED, "The mail is already exist!");
        }
        Member member = memberDTO.toEntity();
        member.hashPassword(bCryptPasswordEncoder);

        return memberRepository.save(member).getId();
    }

    public Optional<Member> findById(Long memberId){
        return memberRepository.findById(memberId);
    }

    private boolean isEmailExists(String mail) {
        Optional<Member> findByMail = memberRepository.findByMail(mail);
        return !findByMail.isEmpty();
    }

    public boolean matchPassword(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
