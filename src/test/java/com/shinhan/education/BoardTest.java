package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.education.repository.BoardRepository;
import com.shinhan.education.vo.BoardVO;
import com.shinhan.education.vo.CarVO;
import com.shinhan.education.vo.QBoardVO;

import lombok.extern.java.Log;

// JUNIT으로 단위 test하기
@SpringBootTest
@Log
class BoardTest {

//	Logger log = LoggerFactory.getLogger(BoardTest.class); 
	
	@Autowired
	BoardRepository brepo;
	
//	@Test
	void dynamicSQLTest() {
		String title2 = "제목"; // and title like '%제목9%'
		Long bno2 = 100L; // and bno>150
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		builder.and(board.title.like("%" + title2 + "%"));
		builder.and(board.bno.gt(bno2));
		builder.and(board.writer.eq("작성자10"));
		
		
		System.out.println(builder);
		// findAll() => CrudRepository에서 제공
		// findAll(predicate) => QuerydslPredicatorExecutor에서 제공
		List<BoardVO> blist = (List<BoardVO>)brepo.findAll(builder);
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
	
//	@Test
	void sample1() {
		long rowCount = brepo.count();
		log.info(rowCount + "건");
		
		boolean result = brepo.existsById(100L);
		log.info(result ? "존재한다": "아니다 존재하지 않는다 !!!");
	}
	
//	@Test
	void sample2() {
		List<BoardVO> blist = brepo.findByTitleContainingOrderByTitleDesc("제목");
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
	
//	@Test
	void sample4() {
		Pageable paging = PageRequest.of(1, 5); // 몇 페이지, 한 페이지사이즈
		List<BoardVO> blist = brepo.findByTitleContainingOrderByTitleDesc("제목", paging);
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
//	@Test
	void sample3() {
//		Sort sort = Sort.by("bno").descending();
		
		// order by writer Desc, bno Desc
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] {"writer", "bno"});// writer있다면 bno desc
		Pageable paging = PageRequest.of(1, 5, sort); // 몇 페이지, 한 페이지사이즈
		List<BoardVO> blist = brepo.findByTitleContaining("제목", paging);
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
	
//	@Test
	void sample5() {
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] {"writer", "bno"});// writer있다면 bno desc
		Pageable paging = PageRequest.of(0, 5, sort); // 몇 페이지, 한 페이지사이즈
		
		Page<BoardVO> result = brepo.findByBnoGreaterThan(100L, paging);
		log.info("페이지당 건수: "+ result.getSize());
		log.info("페이지당 총 수: "+ result.getTotalPages());
		log.info("전체 건수: "+ result.getTotalElements());
		log.info("다음 페이지 정보: "+ result.nextPageable());
		List<BoardVO> blist = result.getContent();
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
//	@Test
	void sample6() {
		List<BoardVO> blist = brepo.findByTitle4("제목", "게시");
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
		
	}
	
	@Test
	void sample7() {
		List<Object[]> blist = brepo.findByTitle5("제목", "게시글");
		blist.forEach(bd -> {
			log.info(Arrays.toString(bd));
		});
	}
	
//	@Test
	void sample8NativeQueryTest() {
		List<BoardVO> blist = brepo.findByTitle6("제목", "내용");
		blist.forEach(bd -> {
			log.info(bd.toString());
		});
	}
	
//	@Test
	public void retrieve1() {
		List<BoardVO> blist = brepo.findByTitle("제목수정");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve2() {
		List<BoardVO> blist = brepo.findByContent("내용수정");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve3() {
		List<BoardVO> blist = brepo.findByWriter("admin");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve4() {
		List<BoardVO> blist = brepo.findByWriterAndTitle("admin", "제목수정");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}

//	@Test
	public void retrieve5() {
		List<BoardVO> blist = brepo.findByTitleContaining("제목");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}

//	@Test
	public void retrieve6() {
		List<BoardVO> blist = brepo.findByTitleStartingWith("제목");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve7() {
		List<BoardVO> blist = brepo.findByTitleEndingWith("수정");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve8() {
		List<BoardVO> blist = brepo.findByTitleNotLike("%게시글제목1%");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
//	@Test
	public void retrieve9() {
		List<BoardVO> blist = brepo.findByTitleContainingAndBnoGreaterThanEqual("게시글", 375L);
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
//	@Test
	public void retrieve10() {
		List<BoardVO> blist = brepo.findByBnoBetweenOrderByBnoDesc(375L, 380L);
		blist.forEach(board -> {
			System.out.println(board);
			board.setContent(null);
			brepo.save(board);
		});
	}
	
//	@Test
	public void retrieve11() {
		List<BoardVO> blist = brepo.findByContentNull();
		blist.forEach(board -> {
			if(board.getBno()%2==0) {
				board.setContent("ABC");
			} else {
				board.setContent("abc");
			}
			System.out.println(board);
			brepo.save(board);
		});
	}
//	@Test
	public void retrieve12() {
		List<BoardVO> blist = brepo.findByContentIgnoreCase("abc");
		blist.forEach(board -> {
			System.out.println(board);
		});
	}
	
	
	
	
	
	// ---------------------------------------------------------------------------------------------------------
	
//	@Test
	public void test6() {
		BoardVO board = brepo.findById(101L).orElse(null);
		if(board != null) {
			board.setContent("내용수정");
			board.setTitle("제목수정");
			board.setWriter("admin");
			brepo.save(board); // 이미 있는 데이터는 update
		}
	}
//	@Test
	public void test5() {
		BoardVO board = brepo.findById(101L).orElse(null);
		System.out.println(board);
	}

//	@Test
	public void test4() {
		brepo.findAll().forEach(brd -> {
			System.out.println(brd);
		});
	}
	
//	@Test
	public void test3() {
		
		IntStream.rangeClosed(1, 100).forEach(i ->{
			BoardVO board = BoardVO.builder()
					.title("게시글제목" + i)
					.content("게시글...."+i)
					.writer("작성자"+(i&10))
					.build();
					brepo.save(board); // save함수에 insert
		});
	}
	
//	@Test
	void test2() {
		CarVO car1 = CarVO.builder()
				.model("B모델")
				.price(3000)
				.build();
		log.info(car1.toString());
	}
	
//	@Test
	void test1() {
		CarVO car1 = new CarVO();
		car1.setModel("A모델");
		car1.setPrice(1000);
		
		CarVO car2 = new CarVO();
		car2.setModel("A모델");
		car2.setPrice(1000);
		
		log.info(car1.toString());
		log.info("같니? " + car1.equals(car2));
	}
}
