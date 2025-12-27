package com.ivaaan.seen.comment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ivaaan.seen.comment.dto.CommentEditDto;
import com.ivaaan.seen.comment.dto.CommentGetDto;
import com.ivaaan.seen.comment.dto.CommentUploadDto;
import com.ivaaan.seen.post.Post;
import com.ivaaan.seen.post.PostRepository;
import com.ivaaan.seen.user.User;
import com.ivaaan.seen.user.UserRepository;

import static org.springframework.http.HttpStatus.*;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // ! TODO WE NEED TO CREATE FRIENDSHIPS TO SEE OTHERS POSTS

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private List<CommentGetDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(c -> new CommentGetDto(
                        c.getId(),
                        c.getUserOwner().getId(),
                        c.getPost().getId(),
                        c.getText(),
                        c.getCreatedAt()))
                .toList();
    }

    public List<CommentGetDto> getCommentsByPostId(Long postId, Long userId) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        return toDtoList(comments);
    }

    @Transactional
    public List<CommentGetDto> uploadComment(
            Long postId,
            Long userId,
            CommentUploadDto dto) {

        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "comment.text.invalid");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "post.not_found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "user.not_found"));

        Comment comment = new Comment(user, post, dto.getText().trim());
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        return toDtoList(comments);
    }

    @Transactional
    public List<CommentGetDto> editComment(
            Long postId,
            Long commentId,
            Long userId,
            CommentEditDto dto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "comment.not_found"));

        if (!comment.getPost().getId().equals(postId)) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "comment.not_in_post");
        }

        if (!comment.getUserOwner().getId().equals(userId)) {
            throw new ResponseStatusException(
                    FORBIDDEN,
                    "comment.edit.forbidden");
        }

        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "comment.text.invalid");
        }

        comment.setText(dto.getText().trim());
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        return toDtoList(comments);
    }

    @Transactional
    public List<CommentGetDto> deleteComment(
            Long postId,
            Long commentId,
            Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "comment.not_found"));

        if (!comment.getPost().getId().equals(postId)) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "comment.not_in_post");
        }

        boolean isCommentOwner = comment.getUserOwner().getId().equals(userId);

        boolean isPostOwner = comment.getPost().getUserOwner().getId().equals(userId);

        if (!isCommentOwner && !isPostOwner) {
            throw new ResponseStatusException(
                    FORBIDDEN,
                    "comment.delete.forbidden");
        }

        // TODO Notify the user if the post owner deletes the comment
        commentRepository.delete(comment);

        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        return toDtoList(comments);
    }

    // TODO Only get comments if users are friends
    public List<CommentGetDto> getCommentsByUserId(
            Long userId,
            Long userIdRequest) {

        List<Comment> comments = commentRepository.findAllByUserIdOrderByCreatedAtAsc(userId);

        return toDtoList(comments);
    }

    public List<CommentGetDto> getCommentsMe(Long userId) {

        List<Comment> comments = commentRepository.findAllByUserIdOrderByCreatedAtAsc(userId);

        return toDtoList(comments);
    }
}
