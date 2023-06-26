package com.personal.post.repository;


import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT new com.personal.post.domain.dto.PostResponseDto(p.title, p.content) FROM Posts p")
    List<PostResponseDto> findAllPosts();

    @Query("SELECT new com.personal.post.domain.dto.PostResponseDto(p.title, p.content) FROM Posts p WHERE p.id = :id")
    Optional<PostResponseDto> findPostById(Long id);
}
