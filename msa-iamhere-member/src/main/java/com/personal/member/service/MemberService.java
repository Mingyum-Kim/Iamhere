package com.personal.member.service;

import com.personal.member.domain.Member;
import com.personal.member.dto.LoginDTO;
import com.personal.member.dto.MemberDTO;
import com.personal.member.exception.AppException;
import com.personal.member.exception.ErrorCode;
import com.personal.member.repository.MemberRepository;
import com.personal.member.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;

    public Member join(MemberDTO memberDTO) {
        if (this.isEmailExists(memberDTO.getMail())) {
            throw new AppException(ErrorCode.MAIL_DUPLICATED);
        }
        Member member = memberDTO.toEntity();
        member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));

        return memberRepository.save(member);
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

        if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            String token = JwtTokenUtil.createToken(member.getMail(), key, expireTimeMs);
            return token;
        } else {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public String getNickname(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        return member.getNickname();
    }
}
