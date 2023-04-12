package com.personal.member.dto;

import com.personal.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String mail;
    private String password;
    private Date birth;

    public Member toEntity(){
        Member member = new Member();
        member.setMail(mail);
        member.setPassword(password);
        member.setBirth(birth);
        return member;
    }
}
