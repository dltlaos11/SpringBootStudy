package com.shinhan.education.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.repository.WebReplyRepository;
import com.shinhan.education.vo3.WebBoard;
import com.shinhan.education.vo3.WebReply;

@RestController 
// 응답문서의 body로, jackson으로 json만드는데 reply가져올때 board의 json가져오는 걸 방지하기 위해 @JsonIgnore
@RequestMapping("/replies")
public class WebReplyController {
	
	@Autowired
	WebReplyRepository replyRepo;
	
	@Autowired
	WebBoardRepository boardRepo;
	
	
	private ResponseEntity<List<WebReply>> makeReturn(Long bno, HttpStatus status){

		WebBoard board = WebBoard.builder().bno(bno).title("").build();
		
		List<WebReply> replies =  replyRepo.findByBoardOrderByRnoDesc(board); // fk
		
		return new ResponseEntity<List<WebReply>>(replies, status);
	}
	
	@PostMapping("/{bno}") // 인자값 받기
	public ResponseEntity<List<WebReply>> insertReply(@RequestBody WebReply reply,
			@PathVariable("bno") Long bno){
		WebBoard board = boardRepo.findById(bno).get();
		reply.setBoard(board);
		replyRepo.save(reply);
		
//		List<WebReply> replies =  replyRepo.findByBoardOrderByRnoDesc(board); // fk

		return makeReturn(bno, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{bno}") // 인자값 받기
	public ResponseEntity<List<WebReply>> selectAllReply(@PathVariable("bno") Long bno) {
//		System.out.println(bno + "보드의 === 모든 댓글 조회 ===");
//		
//		WebBoard board = new WebBoard();
//		board.setBno(bno);
//		
//		List<WebReply> replies =  replyRepo.findByBoardOrderByRnoDesc(board); // fk
		

		return makeReturn(bno, HttpStatus.OK);
	}
	
	
	
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> updateReply(@RequestBody WebReply reply,
			@PathVariable("bno") Long bno){
		WebBoard board = boardRepo.findById(bno).get();
		reply.setBoard(board);
		replyRepo.save(reply);
		
//		List<WebReply> replies =  replyRepo.findByBoardOrderByRnoDesc(board); // fk

		return makeReturn(bno, HttpStatus.OK);
	}

	
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> deleteReply(@PathVariable("bno") Long bno,
			@PathVariable("rno") Long rno
			) {
		replyRepo.deleteById(rno);
		
//		WebBoard board = boardRepo.findById(bno).get();
//		List<WebReply> replies =  replyRepo.findByBoardOrderByRnoDesc(board); // fk
		
		return makeReturn(bno, HttpStatus.OK);
	}
}
