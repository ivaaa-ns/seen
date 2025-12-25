package com.ivaaan.seen.post;

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

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{id}")
    public PostGetDto getPostById(Authentication authentication, @PathVariable String id) {
        log.info("GET /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();
        return this.postService.getPostById(Long.parseLong(id), userId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostGetDto uploadNewPost(Authentication authentication,
            @RequestPart("data") PostNewDto postNewDto,
            @RequestPart("file") MultipartFile file) {
        log.info("POST /post/");
        Long userId = (Long) authentication.getPrincipal();
        FileStorageService.validatePost(file);
        return postService.uploadNewPost(postNewDto, file, userId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(Authentication authentication, @PathVariable String id) {
        log.info("DELETE /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();
        this.postService.deletePostById(Long.parseLong(id), userId);
    }

    @PatchMapping("{id}")
    public PostGetDto patchPostById(Authentication authentication, @RequestBody PostUpdateDto postUpdateDto,
            @PathVariable String id) {
        log.info("PATCH /post/{}", id);
        Long userId = (Long) authentication.getPrincipal();
        return this.postService.patchPostById(postUpdateDto, Long.parseLong(id), userId);
    }

}
