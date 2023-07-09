package com.personal.post.service;

import com.personal.post.client.MemberApiClient;
import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostRequestDto;
import com.personal.post.domain.dto.PostResponseDto;
import com.personal.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;

    private final MemberApiClient memberApiClient;

    public Posts save(PostRequestDto dto) {
        Long memberId = memberApiClient.getCurrentMember();
        log.info("memberId : " + memberId);
        return postRepository.save(dto.toEntity(memberId));
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
