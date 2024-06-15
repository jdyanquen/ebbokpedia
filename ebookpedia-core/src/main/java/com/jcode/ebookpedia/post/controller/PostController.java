package com.jcode.ebookpedia.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcode.ebookpedia.post.dto.PostListingResponse;
import com.jcode.ebookpedia.post.service.PostService;

@RestController
@RequestMapping(value = "/v1/posts")
public class PostController {

	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping
	public PostListingResponse findPosts(@RequestParam String data) {
		return postService.findPosts(data);
	}
}
