package com.personal.post.service;

import com.personal.post.client.MemberApiClient;
import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostRequestDto;
import com.personal.post.domain.dto.PostResponseDto;
import com.personal.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;

    private final MemberApiClient memberApiClient;

    public Posts save(PostRequestDto dto){
        String nickname = memberApiClient.getNickname(1L);
        return postRepository.save(dto.toEntity(nickname));
    }

    public Posts update(Long id, PostRequestDto dto) {
        Posts post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.setTitleContent(dto.getTitle(), dto.getContent());
        return postRepository.save(post);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllPosts();
    }

    public PostResponseDto getPost(Long id) {
        return postRepository.findPostById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
