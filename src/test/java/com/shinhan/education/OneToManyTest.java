package com.shinhan.education;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.repository.PDSFileRepository;
import com.shinhan.education.vo.PDSBoard;
import com.shinhan.education.vo.PDSFile;

import lombok.extern.java.Log;



@SpringBootTest
@Log
public class OneToManyTest {

	@Autowired
	PDSFileRepository fileRepo;
	@Autowired
	PDSBoardRepository boardRepo;
	
	
	@Test
	public void getFileCount() {
		// Eager인 경우
		boardRepo.findAll().forEach(bd ->{
			System.out.println(bd.getPname() + " --> "+ bd.getFiles2().size());
		});
		
		// Lazy인 경우 
		List<Object []> blist = boardRepo.getFilesInfo2();
		blist.forEach(arr -> {
			System.out.println(Arrays.toString(arr));
		});
	}
	
//	@Test
	public void test3() {
		// fetch = FetchType.LAZY
		boardRepo.getFilesInfo(417L).forEach(arr -> {
			System.out.println(Arrays.toString(arr));
			});
		
		// fetch = FetchType.EAGER
		System.out.println("-----------------------------");
		PDSBoard board = boardRepo.findById(417L).orElse(null);
		if(board != null) {
			System.out.println(board.getPname());
			System.out.println(board.getPwriter());
			System.out.println(board.getFiles2());
		}
		}
	


	
//	@Test
	void deleteByBoard() {
		Long bno = 429L;
		boardRepo.deleteById(bno);
	}
	
//	@Test
	void deleteByFile() {
		Long[] ar = {424L, 425L,426L,427L};
		
		Arrays.stream(ar).forEach(bno -> {
			fileRepo.deleteById(bno);
		});
	}
	
//	@Test
	void selectAllBoard() {
		boardRepo.findAll().forEach(bd -> {
			log.info("보드이름: " +bd.getPname() + " 작성자: "+ bd.getPwriter() +
					" 첨부file건수: " + bd.getFiles2().size()+ "건");
		});
	}
	
//	@Test
	void insertAll() {
//		List<PDSFile> flist =new ArrayList<>();
//		IntStream.range(1, 6).forEach(i ->{
//			PDSFile files2 = PDSFile.builder()
//					.pdsfilename("firstzone-"+i+".file")
//					.build();
//			flist.add(files2);
//		});

		// insert board -> insert files -> update files
		IntStream.range(20, 30).forEach(j -> {
			
			List<PDSFile> flist =new ArrayList<>();
			IntStream.range(1, 6).forEach(i ->{
				PDSFile files2 = PDSFile.builder()
						.pdsfilename("firstzone-"+i+".file")
						.build();
				flist.add(files2);
			});
			
			PDSBoard board = PDSBoard.builder()
					.pname("@OneToMany로 만든 칼럼은 그 테이블에 없다구")
					.pwriter("은빈")
					.files2(flist)
					.build();
			boardRepo.save(board);
		});

	}
}
