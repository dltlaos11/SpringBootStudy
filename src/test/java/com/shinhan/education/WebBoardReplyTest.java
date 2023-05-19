package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.repository.WebReplyRepository;
import com.shinhan.education.vo3.WebBoard;
import com.shinhan.education.vo3.WebReply;

@SpringBootTest
public class WebBoardReplyTest {

	@Autowired
	WebBoardRepository boardRepo;
	
	@Autowired
	WebReplyRepository replyRepo;
	
	// board에 insert 100건
//	@Test
	void test1() {
		
		IntStream.rangeClosed(300, 400).forEach(i -> {
			
			WebBoard entity = WebBoard.builder()
					.title("webBoard" + i)
					.writer("user" + (i/100))
					.content("Spring Boot PJ....")
					.build();

			boardRepo.save(entity);			
		});

	}
	
	// 5개의 board에 댓글 10개 넣기
//	@Test
	void test2() {
		Long[] arr = {46L, 47L, 48L, 59L, 60L};
		Arrays.stream(arr).forEach(bno -> {
			boardRepo.findById(bno).ifPresent(bd -> {
				IntStream.rangeClosed(20, 30).forEach(idx -> {
					WebReply reply = WebReply.builder()
							.replyText("날씨가 더워요.."+idx)
							.replyer("댓글직상자-"+idx)
							.board(bd)
							.build();
					
					replyRepo.save(reply);
				});
			});
		});
	}
	// 모든 board 조회
//	@Test
	void test3() {
		boardRepo.findAll().forEach(bd -> {
			System.out.println(bd);
		});
	}

	// 특정 board댓글(Reply에서 시작)
//	@Test
	void test5() {
		WebBoard board = boardRepo.findById(47L).get();
		List<WebReply> replylist = replyRepo.findByBoardOrderByRnoDesc(board);
		replylist.forEach(reply -> {
			System.out.println(reply);
		});
	}
	
	// 특정 board댓글(Board에서 시작)
//	@Test
	void test4() {
		boardRepo.findById(47L).ifPresent(bd -> {
			List<WebReply> replyList = bd.getReplies();
			for(WebReply reply: replyList) {
				System.out.println(reply);
			}
		});
	}
	// board별 댓글 수
	
}
