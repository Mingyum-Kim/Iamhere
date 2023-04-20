package com.personal.member.service;

import com.personal.member.domain.Member;
import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.exception.AppException;
import com.personal.member.exception.ErrorCode;
import com.personal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long insertMember(MemberDTO memberDTO) {
        if (this.isEmailExists(memberDTO.getMail())) {
            throw new AppException(ErrorCode.MAIL_DUPLICATED);
        }
        Member member = memberDTO.toEntity();
        member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));

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

    public String login(LoginDTO loginDTO) {
        Member member = memberRepository.findByMail(loginDTO.getMail())
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        String encodedPassword = bCryptPasswordEncoder.encode(loginDTO.getPassword());
        if(bCryptPasswordEncoder.matches(encodedPassword, member.getPassword())){
            return member.getMail();
        } else {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
