package com.personal.post.service;

import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostRequestDto;
import com.personal.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;

    public Posts save(PostRequestDto dto){
        return postRepository.save(dto.toEntity());
    }

    public Posts update(Long id, PostRequestDto dto) {
        Posts post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.setTitleContent(dto.getTitle(), dto.getContent());
        return postRepository.save(post);
    }

    public List<Posts> getPosts() {
        return postRepository.findAll();
    }

    public Posts getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
