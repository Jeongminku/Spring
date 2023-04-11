package com.wss.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wss.dto.FeedDto;
import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
	private final FeedRepository feedRepository;
	
	public Feed saveFeed(Feed feed) {
		return feedRepository.save(feed);
	}
	
	public List<Feed> Feedjoinbroad() {
		return feedRepository.Feedjoinbroad();
	}
	
	public void feedDel(Long id) {
		Feed feed = feedRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		feedRepository.feedDel(feed.getId());
	}
	
	public Optional<Feed> findFeed(Long id) {
		return feedRepository.findById(id);
	}
}
