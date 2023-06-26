package com.personal.post.controller;

import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostRequestDto;
import com.personal.post.domain.dto.PostResponseDto;
import com.personal.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Posts> save(@RequestBody PostRequestDto dto) {
        return new ResponseEntity<>(postService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Posts> update(@RequestParam Long id, @RequestBody PostRequestDto dto) {
        return new ResponseEntity<>(postService.update(id, dto), HttpStatus.OK);
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<PostResponseDto> getPost(@RequestParam Long id){
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
