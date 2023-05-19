package com.shinhan.education;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.repository.PDSFileRepository;
import com.shinhan.education.vo.PDSBoard;
import com.shinhan.education.vo.PDSFile;

import lombok.ToString;

@SpringBootTest
@Commit // @Transactional
// Test환경에서만 적용
public class OneToManyTest2 {
	@Autowired
	PDSFileRepository fileRepo;
	
	@Autowired
	PDSBoardRepository boardRepo;
	
	// Board를 이용해서 File이름 입력하기
//	@Test
	void boardFileInsert() {
		boardRepo.findById(3L).ifPresent(board -> {
			List<PDSFile> files2 = board.getFiles2();
			files2.get(1).setPdsfilename("용준@@@@@@@@@");
			PDSFile file =PDSFile.builder()
					.pdsfilename("추가3 !!!!!!!!!!")
					.build();
			PDSFile file2 =PDSFile.builder()
					.pdsfilename("추가4 !!!!!!!!!!")
					.build();
			files2.add(file);
			files2.add(file2);
			boardRepo.save(board);
		});
	}
	
	// Board를 이용해서 File이름 수정하기
//	@Test
	void boardFileUpdate() {
		boardRepo.findById(3L).ifPresent(board -> {
			List<PDSFile> files2 = board.getFiles2();
			files2.forEach(f -> {
				f.setPdsfilename("!!!!!!!!!!!!1수정 !!!~~~");
				fileRepo.save(f);
			});
		});
	}
	
	// PDSBoardRepository(부모)를 이용하여 자식을 수정
	@Transactional // @Modifying을 사용한 경우 필요, 하나의 transaction을 묶는다. PDSBoardRepository.java에서 어노테이션 추가
	// 실행은 성공하지만 Test환경은 Rollback처리되므로 db 반영안됨 -> 해결하고 싶으면 최상단(class level)에 @Commit 추가
	// Test에 @Transactional: Test 후 Rollback 처리한다. test환경에서 @Transactional사용하는게 맞다고 하신다.
//	@Test
	void fileUpdate() {
		boardRepo.updateFile(4L, "풍경사진");
	}
	
//	@Transactional // LAZY인 경우... 자식의 접근하기 위해
//	@ToString(exclude = "files2") // 모든 칼럼 가져오는거 방지 서로..files2는 자시에서 X
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY) files2
//	@Test
	void test7() {
		boardRepo.findAll().forEach(b -> {
			System.out.println(b);
			System.out.println(b.getFiles2());
		});
	}
	
	// 1-n: 부모에서 자식을 insert하자
//	@Test
	void test6() {
		List<PDSFile> files = new ArrayList<>();
		PDSFile file = PDSFile.builder()
				.pdsfilename("얼굴사진1")
				.build();
		PDSFile file1 = PDSFile.builder()
				.pdsfilename("얼굴사진2")
				.build();
		PDSFile file2 = PDSFile.builder()
				.pdsfilename("얼굴사진3")
				.build();
		files.add(file);
		files.add(file1);
		files.add(file2);
		PDSBoard board = PDSBoard.builder()
				.pname("월요일이다")
				.pwriter("진우")
				.files2(files)
				.build();
		
		boardRepo.save(board);
	}
	
	/* 자바에서 칼럼이 없으므로 다음 방법은 불가 ... */
	@Test
	void test5() {
		PDSFile file = fileRepo.findById(1L).orElse(null); // 1개찾기
		if(file != null) {
//			PDSBoard board = boardRepo.findById(2L).orElse(null);
//			fileRepo.save(board); fileRepo에 없으므로 자바에서 불가
			
			file.setPdsfilename("파일이름 수정");
			fileRepo.save(file);
		}
	}
	
//	@Test
	void test4() {
		boardRepo.findAll().forEach(b-> System.out.println(b));
		// b.toString() 
	}
	
	// 부모만 insert
//	@Test
	void test3() {
		PDSBoard board = PDSBoard.builder()
				.pname("게시글")
				.pwriter("작성자")
				.build();
		boardRepo.save(board);
	}
	
//	@Test
	void test2() {
		fileRepo.findAll().forEach(f -> System.out.println(f));
	}
	
	// 자식만 issert
//	@Test
	void test1() {
		PDSFile file = PDSFile
				.builder()
				.pdsfilename("첨부파일1")
				.build();
		fileRepo.save(file);
	}
}
