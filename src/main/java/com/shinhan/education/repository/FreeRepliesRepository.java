package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.FreeBoard;
import com.shinhan.education.vo.FreeBoardReply;

// findAll(), findById(댓글번호가 key)
public interface FreeRepliesRepository extends CrudRepository<FreeBoardReply, Long>{
	
	List<FreeBoardReply> findByBoard(FreeBoard board);
}
