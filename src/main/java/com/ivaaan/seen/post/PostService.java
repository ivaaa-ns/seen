package com.ivaaan.seen.post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.ivaaan.seen.post.dto.PostGetDto;
import com.ivaaan.seen.post.dto.PostNewDto;
import com.ivaaan.seen.uploads.FileStorageService;
import com.ivaaan.seen.user.User;
import com.ivaaan.seen.user.UserRepository;
import com.ivaaan.seen.user.dto.UserMeDto;

public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository,
            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostGetDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        return new PostGetDto(post.getId(), post.getUserOwner().getId(), post.getPhoto(), post.getDescription(),
                post.getHidden());

    }

    public PostGetDto uploadNewPost(PostNewDto postNewDto, MultipartFile photo) {

        User userOwner = userRepository.findById(postNewDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post savedPost = postRepository.save(new Post(
                userOwner,
                "",
                postNewDto.getDescription(),
                postNewDto.getHidden()));

        savedPost.setPhoto(FileStorageService.uploadUserPost(
                photo,
                userOwner.getId(),
                savedPost.getId()));

        Post finalPost = postRepository.save(savedPost);

        return new PostGetDto(
                finalPost.getId(),
                finalPost.getUserOwner().getId(),
                finalPost.getPhoto(),
                finalPost.getDescription(),
                finalPost.getHidden());
    }
}
