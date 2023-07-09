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
    private String nickname;
    private Date birth;

    public Member toEntity(){
        return Member.builder()
                .mail(mail)
                .password(password)
                .nickname(nickname)
                .birth(birth)
                .build();
    }
}
