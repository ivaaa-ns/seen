package com.ivaaan.seen.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByCreatedAtAsc(Long postId);

    List<Comment> findAllByUserIdOrderByCreatedAtAsc(Long postId);

}
