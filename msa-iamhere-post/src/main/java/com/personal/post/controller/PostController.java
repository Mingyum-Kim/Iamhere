package com.personal.post.controller;

import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostSaveRequestDto;
import com.personal.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Posts> save(@RequestBody PostSaveRequestDto dto) {
        return ResponseEntity.ok(postService.save(dto));
    }
}
