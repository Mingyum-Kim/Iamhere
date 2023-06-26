package com.personal.post.service;

import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostSaveRequestDto;
import com.personal.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;

    public Posts save(PostSaveRequestDto dto){
        return postRepository.save(dto.toEntity());
    }
}
