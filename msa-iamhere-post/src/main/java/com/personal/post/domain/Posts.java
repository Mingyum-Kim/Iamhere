package com.personal.post.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Posts extends CommonDateEntity{
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String author;

    public Posts(String title, String content){
        this.title = title;
        this.content = content;
    }

    public static Posts save(String title, String content){
        return new Posts(title, content);
    }

    public void setTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
