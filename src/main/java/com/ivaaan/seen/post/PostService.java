package com.ivaaan.seen.post;

import org.springframework.web.multipart.MultipartFile;

import com.ivaaan.seen.post.dto.PostGetDto;
import com.ivaaan.seen.post.dto.PostNewDto;
import com.ivaaan.seen.post.dto.PostUpdateDto;
import com.ivaaan.seen.uploads.FileStorageService;
import com.ivaaan.seen.user.User;
import com.ivaaan.seen.user.UserRepository;

public class PostService {
        private PostRepository postRepository;
        private UserRepository userRepository;

        public PostService(PostRepository postRepository,
                        UserRepository userRepository) {
                this.postRepository = postRepository;
                this.userRepository = userRepository;
        }

        // TODO Validate owner if hidden
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

        // TODO Validate owner
        public PostGetDto patchPostById(PostUpdateDto postUpdateDto, Long id) {

                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Post not found"));

                if (postUpdateDto.getDescription() != null) {
                        post.setDescription((postUpdateDto.getDescription()));
                }

                if (postUpdateDto.getHidden() != null) {
                        post.setHidden(postUpdateDto.getHidden());
                }

                Post postSaved = postRepository.save(post);

                return new PostGetDto(
                                postSaved.getId(),
                                postSaved.getUserOwner().getId(),
                                postSaved.getPhoto(),
                                postSaved.getDescription(),
                                postSaved.getHidden());

        }

        // TODO Validate owner
        public void deletePostById(Long id) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Post not found"));

                postRepository.delete(post);
        }

}
