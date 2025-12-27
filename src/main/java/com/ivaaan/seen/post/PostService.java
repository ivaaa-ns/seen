package com.ivaaan.seen.post;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ivaaan.seen.post.dto.PostGetDto;
import com.ivaaan.seen.post.dto.PostNewDto;
import com.ivaaan.seen.post.dto.PostUpdateDto;
import com.ivaaan.seen.uploads.FileStorageService;
import com.ivaaan.seen.user.User;
import com.ivaaan.seen.user.UserRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
public class PostService {
        private PostRepository postRepository;
        private UserRepository userRepository;

        public PostService(PostRepository postRepository,
                        UserRepository userRepository) {
                this.postRepository = postRepository;
                this.userRepository = userRepository;
        }

        public PostGetDto getPostById(Long id, Long userId) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "post.not_found"));

                if (post.getHidden() && !post.getUserOwner().getId().equals(userId)) {
                        throw new AccessDeniedException("post.hidden");
                }

                return new PostGetDto(post.getId(), post.getUserOwner().getId(), post.getPhoto(), post.getDescription(),
                                post.getHidden());

        }

        public PostGetDto uploadNewPost(PostNewDto postNewDto, MultipartFile photo, Long userId) {

                User userOwner = userRepository.findById(userId)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user.not_found"));

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

        public PostGetDto patchPostById(PostUpdateDto postUpdateDto, Long id, Long userId) {

                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "post.not_found"));

                if (!post.getUserOwner().getId().equals(userId)) {
                        throw new AccessDeniedException("post.not_owner");

                }

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

        public void deletePostById(Long id, Long userId) {

                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "post.not_found"));

                if (!post.getUserOwner().getId().equals(userId)) {
                        throw new AccessDeniedException("post.not_owner");
                }

                postRepository.delete(post);
        }

        public List<PostGetDto> getPostByUserId(Long id) {

                return postRepository.findAllByUserOwnerIdAndHiddenFalse(id)
                                .stream()
                                .map(post -> new PostGetDto(
                                                post.getId(),
                                                post.getUserOwner().getId(),
                                                post.getPhoto(),
                                                post.getDescription(),
                                                post.getHidden()))
                                .toList();
        }

}
