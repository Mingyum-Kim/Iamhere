package com.personal.post.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Posts extends CommonDateEntity{
    @Id @GeneratedValue
    private Long id;
    private String title;

    private Long memberId;

    private String authorName;

    private String content;


    public Posts(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Posts(String title, String content, Long memberId) {
        this.id = id;
        this.title = title;
        this.memberId = memberId;
    }

    public void setTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
