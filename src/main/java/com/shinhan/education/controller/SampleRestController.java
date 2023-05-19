package com.shinhan.education.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.education.repository.BoardRepository;
import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.vo.BoardVO;
import com.shinhan.education.vo.CarVO;
import com.shinhan.education.vo.QBoardVO;

import lombok.extern.java.Log;

@RestController // @Contorller + @ResponseBody
				// response.getWriter().append("jsp/servlet");
@Log
public class SampleRestController {

	@Autowired
	BoardRepository brepo;
	@Autowired
	PDSBoardRepository pdsBoard;
	
	@GetMapping("/monday")
	@Transactional // @Modifying을 사용한 경우 필요, 하나의 transaction을 묶는다.
	// 실행은 성공하지만 Test환경은 Rollback처리되므로 db 반영안됨 -> 해결하고 싶으면 최상단(class level)에 @Commit 추가
	String fileUpdate() {
		int result = pdsBoard.updateFile(4L, "풍경사진 @@@@@");
		return "OK" + result;
	}
	
	
	@GetMapping("/sunday")
	public List<BoardVO> dynamicSQLTest() {
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
		
		return blist;
	}
	
	
	@GetMapping("/friday")
	public Map<String, Object> sample1() {
		long rowCount = brepo.count();
		log.info(rowCount + "건");
		
		boolean result = brepo.existsById(100L);
		log.info(result ? "존재한다": "아니다 존재하지 않는다 !!!");
		
		Map<String, Object> map = new HashMap<>();
		map.put("aa", rowCount+"건");
		map.put("bb",result ? "존재한다": "아니다 존재하지 않는다 !!!");
		map.put("data", brepo.findById(200L).orElse(null));
		return map;
	}

	
	@GetMapping("/cartest1")
	public List<CarVO> test4() { //Jackson이 JAVA객체를 JSON으로 만들어서 return
		
		List<CarVO> carlist = new ArrayList<>();
		IntStream.rangeClosed(1, 10).forEach(idx -> {
			CarVO car1 = CarVO.builder()
					.model("BMW520-----"+idx)
					.price(6000)
					.build();
			carlist.add(car1);
		});
		
		return carlist;
	}
	
	@GetMapping("/cartest")
	public CarVO test3() { //Jackson이 JAVA객체를 JSON으로 만들어서 return
		CarVO car1 = CarVO.builder()
				.model("BMW520")
				.price(6000)
				.build();
		return car1;
	}
	
	
	// @RequestMapping(value="/samle1", method=RequestMethod.GET)
	@GetMapping("/sample1")
	public String test1() {
		return "SpringBoot ����";
	}
	@GetMapping("/sample2")
	public String test2() {
		return "SpringBoot --- �ߵǱ� �ٶ��ϴ� ---";
	}
}
