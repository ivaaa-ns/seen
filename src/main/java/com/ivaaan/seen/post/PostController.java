package com.ivaaan.seen.post;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ivaaan.seen.post.dto.PostGetDto;
import com.ivaaan.seen.post.dto.PostNewDto;
import com.ivaaan.seen.post.dto.PostUpdateDto;
import com.ivaaan.seen.uploads.FileStorageService;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/userId/{id}")
    public List<PostGetDto> getPostByUserId(Authentication authentication, @PathVariable Long id) {
        log.info("GET /post/userId/{}", id);
        return postService.getPostByUserId(id);

    }

    @GetMapping("{id}")
    public PostGetDto getPostById(
            Authentication authentication,
            @PathVariable Long id) {
        log.info("GET /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();

        return postService.getPostById(id, userId);
    }

    // ! Care with data string
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostGetDto uploadNewPost(
            Authentication authentication,
            @RequestPart("data") String data,
            @RequestPart("file") MultipartFile file) throws Exception {

        PostNewDto postNewDto = new ObjectMapper().readValue(data, PostNewDto.class);

        Long userId = (Long) authentication.getPrincipal();
        FileStorageService.validatePost(file);

        return postService.uploadNewPost(postNewDto, file, userId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(Authentication authentication, @PathVariable Long id) {
        log.info("DELETE /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();
        this.postService.deletePostById(id, userId);
    }

    @PatchMapping("{id}")
    public PostGetDto patchPostById(Authentication authentication, @RequestBody PostUpdateDto postUpdateDto,
            @PathVariable Long id) {
        log.info("PATCH /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();
        return this.postService.patchPostById(postUpdateDto, id, userId);
    }

}
