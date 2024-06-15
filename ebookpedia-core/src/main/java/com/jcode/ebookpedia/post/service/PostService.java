package com.jcode.ebookpedia.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcode.ebookpedia.criteria.Criteria;
import com.jcode.ebookpedia.criteria.CriteriaMapper;
import com.jcode.ebookpedia.post.dto.PostListingResponse;
import com.jcode.ebookpedia.post.mapper.PostMapper;
import com.jcode.ebookpedia.post.model.Post;
import com.jcode.ebookpedia.post.repository.PostRepository;


@Service
public class PostService {
	
	private static final Logger log = LoggerFactory.getLogger(PostService.class);

	private final PostRepository postRepository;
	
	private final CriteriaMapper criteriaMapper;
	
	public PostService(PostRepository postRepository, CriteriaMapper criteriaMapper) {
		this.postRepository = postRepository;
		this.criteriaMapper = criteriaMapper;
	}
	
	@Transactional(readOnly = true)
	public PostListingResponse findPosts(String data) {
		String mappedCriteria = this.criteriaMapper.map(Criteria.fromBase64String(data));
			
		log.info(mappedCriteria);
			
		List<Post> posts = this.postRepository.findPosts(mappedCriteria);
		
		PostListingResponse response = new PostListingResponse();
		response.setRecords(posts.stream().map(PostMapper.INSTANCE::map).collect(Collectors.toList()));
		return response;
	}
	
}
