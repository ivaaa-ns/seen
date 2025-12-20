package com.ivaaan.seen.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    private PostGetDto getPostById(@PathVariable String id) {
        log.info("GET /post/{}", id);
        return this.postService.getPostById(Long.parseLong(id));
    }

    // TODO We need the user ID from the JWT
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostGetDto uploadNewPost(
            @RequestPart("data") PostNewDto postNewDto,
            @RequestPart("file") MultipartFile file) {
        log.info("POST /post/");
        FileStorageService.validatePost(file);
        return postService.uploadNewPost(postNewDto, file);
    }

    // TODO We need the user ID from the JWT
    // TODO We need to return status good
    @DeleteMapping("{id}")
    private PostGetDto deletePostById(@PathVariable String id) {
        log.info("DELETE /post/{}", id);
        return this.postService.deletePostById(id);
    }

    @PatchMapping("{id}")
    private PostGetDto patchPostById(@RequestBody PostUpdateDto postUpdateDto, @PathVariable String id) {
        log.info("PATCH /post/{}", id);
        return this.postService.patchPostById(postUpdateDto, id);
    }

}
