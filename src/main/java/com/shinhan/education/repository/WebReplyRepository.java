package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.education.vo3.WebBoard;
import com.shinhan.education.vo3.WebReply;

public interface WebReplyRepository extends PagingAndSortingRepository<WebReply, Long>{
	
	public List<WebReply> findByBoardOrderByRnoDesc(WebBoard board); // jpa

}
