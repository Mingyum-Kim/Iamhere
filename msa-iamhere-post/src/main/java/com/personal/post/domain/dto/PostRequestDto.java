package com.personal.post.domain.dto;

import com.personal.post.domain.Posts;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;

    public Posts toEntity(){
        return new Posts(title, content);
    }
}
