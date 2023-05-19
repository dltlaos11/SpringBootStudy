package com.shinhan.education.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.education.vo3.QWebBoard;
import com.shinhan.education.vo3.WebBoard;

public interface WebBoardRepository extends PagingAndSortingRepository<WebBoard, Long>,
QuerydslPredicateExecutor<WebBoard>{
	// PagingAndSortingRepository : crud + paging + sort
	// QuerydslPredicateExecutor : Querydsl
	
	// interface: 상수, 추상메서드, default 메서드, static메서드
	// type에 따라 keyword가 달라지는
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoard board = QWebBoard.webBoard;
		builder.and(board.bno.gt(0)); // and bno > 0
		//검색조건처리
		if(type==null) return builder;
		switch (type) {
		case "title": // and title like ?
			builder.and(board.title.like("%" + keyword + "%")); break;
		case "content": // and content like ?
			builder.and(board.content.like("%" + keyword + "%")); break;
		case "writer": // and writer like ?
			builder.and(board.writer.like("%" + keyword + "%")); break;
		default: break;
		}
		return builder;
		}
	
}
