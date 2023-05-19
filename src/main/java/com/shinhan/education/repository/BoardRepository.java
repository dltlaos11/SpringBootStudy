package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shinhan.education.vo.BoardVO;

// interface설계... CRUD작업을 위해
// 구현은 JPA가 한다.
//1. 기본은 findAll(), findById(), save(), count(), exists()
//2. 규칙에 맞는 메서드 추가 findByXXXXX
//3. JPQL 사용 : @Query
//4. JPQL 사용 : @Query , nativeQuery=true를 사용하여 직접 SQL작성가능

//@Repository
public interface BoardRepository extends CrudRepository<BoardVO, Long>,
							QuerydslPredicateExecutor<BoardVO>{
	
	// 조건조회 추가하기
	public List<BoardVO> findByTitle(String title);
	public List<BoardVO> findByContent(String aa);
	public List<BoardVO> findByWriter(String writer);
	public List<BoardVO> findByWriterAndTitle(String writer, String title);
	public List<BoardVO> findByTitleNotLike(String title);
	
	public List<BoardVO> findByTitleContainingOrderByTitleDesc(String title);
	// where title like ? order by title desc
	
	public List<BoardVO> findByTitleContaining(String title); 
	// select * from t_boards where title like '%?%'
	public List<BoardVO> findByTitleStartingWith(String title); 
	// select * from t_boards title like '?%'
	public List<BoardVO> findByTitleEndingWith(String title); 
	// select * from t_boards title like '%?'
	
	public List<BoardVO> findByTitleContainingAndBnoGreaterThanEqual(String title, Long bno); 
	// select * from t_boards title like '%?%' and bno > ?
	
	public List<BoardVO> findByBnoBetweenOrderByBnoDesc(Long bno1, Long bno2);
	// where bno between ? and ? order by bno dec
	
	public List<BoardVO> findByContentNull(); // IsNull() 또는 Null
	// where content is null
	
	public List<BoardVO> findByContentIgnoreCase(String content);
	// where upper(content) = upper(?)
	
	public List<BoardVO> findByTitleContainingOrderByTitleDesc(String title, Pageable paging);
	// title로 조회, sort desc, paging => page, size

	public List<BoardVO> findByTitleContaining(String title, Pageable paging);
	public Page<BoardVO> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// JPQL(JPL Query Language)...*지원안됨
//	@Query("select * from t_boards where title like ? order by bno desc")
	@Query("select b from BoardVO b where b.title like %?1% "
			+ "and b.content like %?2% order by b.bno desc")
	public List<BoardVO> findByTitle2(String title, String content);
	
//	Param
	@Query("select b from BoardVO b where b.title like %:tt% "
			+ " and b.content like %:cc% order by b.bno desc")
	public List<BoardVO> findByTitle3(@Param("tt") String title,@Param("cc")  String content);
	
//	EntityName
	@Query("select b from #{#entityName} b where b.title like %:tt% "
			+ "and b.content like %:cc% order by b.bno desc")
	public List<BoardVO> findByTitle4(@Param("tt") String title,@Param("cc")  String content);
	
	@Query("select b.title,b.content,b.writer  from #{#entityName} b where b.title like %?1% "
			+ " and b.content like %?2% order by b.bno desc")
	public List<Object[]> findByTitle5(String title, String content);
	
	//SQL문을 직접 작성한다, nativeQuery = true(난용하지 않기)
	@Query(value = "select * from t_boards  where title like '%'||?1||'%' "
			+ " and content like '%'||?2||'%' order by bno desc", nativeQuery = true)
	public List<BoardVO> findByTitle6(String title, String content);
}
