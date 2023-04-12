package com.personal.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private String password;

    private Date birth;

    private LocalDateTime joinedAt = LocalDateTime.now();
}
