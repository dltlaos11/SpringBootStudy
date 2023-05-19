package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.ManyToOne;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shinhan.education.repository.FreeBoardRepository;
import com.shinhan.education.repository.FreeRepliesRepository;
import com.shinhan.education.vo.FreeBoard;
import com.shinhan.education.vo.FreeBoardReply;

// 양방향 Test

@SpringBootTest
public class OneToManyToOneTest {
	
	@Autowired
	FreeBoardRepository boardRepo;
	@Autowired
	FreeRepliesRepository replyRepo;
	
	
	
	// 댓글에 board번호 100번 댓글 삭제... 댓글도 삭제된다.
//	@Test
	void test7() {
//		boardRepo.findById(100L).ifPresent(bd -> {
//			boardRepo.delete(bd);
//		});
		boardRepo.deleteById(100L);
	}
	
	
	// board번호가 100이상인 board조회 ..paging 추가
//	@Test
	void test6() {
		Pageable paging = PageRequest.of(0, 3, Sort.Direction.ASC, "bno"); //페이지 크기 3
		List<FreeBoard> blist = boardRepo.findByBnoGreaterThan(99L, paging);
		blist.forEach(bd -> {
			System.out.println(bd.getTitle() + " ==> " + bd.getReplies().size());
		});
	}
	
//	@Test // 댓글 모두조회(n:1이용)...boardno가 15번인 댓글 조회
	void test5() {
		boardRepo.findById(15L).ifPresent(bd -> {
			replyRepo.findByBoard(bd).forEach(reply -> {
				System.out.println(reply + "==> "+ reply.getBoard().getBno());
			});
		});
	}
	
//	@Test // 댓글 모두조회(n:1이용)
	void test4() {
		replyRepo.findAll().forEach(re -> {
			System.out.println(re.getReply() + " ==> "+re.getBoard().getWriter());
			// re.getBoard().getWriter() == 
			// @ManyToOne  연관관계 설정: n:1
			// FK: tbl_free_replies dp 칼럼이 board_bno 생성
			// FreeBoard board;
		});
	}
	
	
//	@Test // board모두 조회(1:n 이용)
	void test3() {
		boardRepo.findAll().forEach(bd -> {
			// board번호와 댓글 갯수
			System.out.println(bd.getBno() + "==> "+ bd.getReplies().size());
		});
	}
	
	// 15번 board 댓글 insert 10건
	@Test // boardRepo가 CRUDREPO 상속받음, 그안에 findById존재로 값 찾기
	void test2() {
		Long[] arr = {1L, 10L, 50L, 100L};
		Arrays.stream(arr).forEach(idx -> {
			
			boardRepo.findById(idx).ifPresent(bd -> { 
				IntStream.rangeClosed(1, 10).forEach(i -> {// 값 setting시 해당 vo에서 자동들어갈값 말고 넣어줄 값설정
					FreeBoardReply reply = FreeBoardReply.builder() 
							.reply("화요일" + i)
							.replyer("user" + (i%2))
							.board(bd)
							.build();
					replyRepo.save(reply);
				});
			});
		});
	}
	
	//board insert 100건
//	@Test
	void test1() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			FreeBoard board = FreeBoard.builder()
					.title("게시글 제목" + i)
					.writer("user"+(i%10))
					.content("게시글.....")
					.build();
			boardRepo.save(board);
		});
	}
}
