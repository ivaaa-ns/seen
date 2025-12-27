package com.ivaaan.seen.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOwnerId(Long userId);

    List<Post> findAllByUserOwnerIdAndHiddenFalse(Long userId);

}
