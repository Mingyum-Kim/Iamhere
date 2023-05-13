package com.personal.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private String password;

    private Date birth;

    private LocalDate joinedAt = LocalDate.now();

    public Member hashPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(plainPassword, this.password);
    }
}
