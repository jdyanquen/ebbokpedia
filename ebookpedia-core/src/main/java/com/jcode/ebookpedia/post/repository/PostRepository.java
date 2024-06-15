package com.jcode.ebookpedia.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcode.ebookpedia.post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
	
	@Query(value = " select * from find_posts(CAST(?1 AS criteria))", nativeQuery = true)
	List<Post> findPosts(String criteria);

}
