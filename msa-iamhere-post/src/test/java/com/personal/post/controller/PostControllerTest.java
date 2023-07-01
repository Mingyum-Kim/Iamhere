package com.personal.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.post.domain.Posts;
import com.personal.post.domain.dto.PostRequestDto;
import com.personal.post.domain.dto.PostResponseDto;
import com.personal.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private PostController postController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    String title1, title2, content1, content2;
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        title1 = "Hello, World. This is the title of first post.";
        content1 = "Hello, World. This is the contents of first post. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. ";

        title2 = "Hello, World. This is the title of second post.";
        content2 = "Hello, World. This is the contents of second post. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    }

    @DisplayName("게시글 저장에 성공한다.")
    @Test
    public void savePost() throws Exception {
        PostRequestDto dto = new PostRequestDto(title1, content1);
        Posts expected = new Posts(title1, content1);
        when(postService.save(any())).thenReturn(expected);

        String responseBody = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @DisplayName("게시글 수정에 성공한다.")
    @Test
    public void updatePost() throws Exception {
        PostRequestDto dto = new PostRequestDto(title2, content2);
        Posts expected = new Posts(title2, content2);
        when(postService.update(anyLong(), any())).thenReturn(expected);

        String responseBody = mockMvc.perform(put("/api/v1/posts")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @DisplayName("게시글 단일 조회에 성공한다.")
    @Test
    public void getPost() throws Exception {
        PostResponseDto expected = new PostResponseDto(title1, content1);
        when(postService.getPost(anyLong())).thenReturn(expected);

        String responseBody = mockMvc.perform(get("/api/v1/posts")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @DisplayName("게시글 전체 조회에 성공한다.")
    @Test
    public void getPosts() throws Exception {
        PostResponseDto dto1 = new PostResponseDto(title1, content1);
        PostResponseDto dto2 = new PostResponseDto(title2, content2);
        List<PostResponseDto> expected = new ArrayList<>();
        expected.add(dto1);
        expected.add(dto2);
        when(postService.getPosts()).thenReturn(expected);

        String responseBody = mockMvc.perform(get("/api/v1/posts/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expected));
    }

    @DisplayName("게시글 삭제에 성공한다.")
    @Test
    public void deletePost() throws Exception {

        doNothing().when(postService).deletePost(anyLong());

        mockMvc.perform(delete("/api/v1/posts")
                        .param("id", "1"))
                .andExpect(status().isNoContent());
    }
}