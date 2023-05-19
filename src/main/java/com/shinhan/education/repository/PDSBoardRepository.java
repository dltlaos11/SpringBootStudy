package com.shinhan.education.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.PDSBoard;
import com.shinhan.education.vo.PDSFile;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long>{
	
	// Query는 select만 가능, DML사용시 @Modifying를 반드시 함께 사용
	@Modifying 
	//@Transactional // Test환경이 아니라 상속받는 interface에 존재시 commit
	@Query("update from PDSFile f set f.pdsfilename=?2 where f.fno =?1  ") // Entity이름 VO명시
	int updateFile(Long fno, String newFileName); 
	
	// fetch = FetchType.LAZY인 경우 JPQL를 이용할 수 있다.
	@Query("select b.pname, b.pwriter, f.pdsfilename "
			+ " from PDSBoard b left outer join b.files2 f "
			+ " where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo(long pid); // 칼럼나열 object로 받아야 
	
	// fetch = FetchType.LAZY인 경우 nativeQuery를 이용할 수 있다.
	@Query(value = "select board2.pname, count(*) "
			+ " from tbl_pdsboard board2 left outer join tbl_pdsfiles file2 "
			+ " on(board2.pid = file2.pdsno) group by board2.pname ", nativeQuery = true)
	public List<Object []> getFilesInfo2();


}
