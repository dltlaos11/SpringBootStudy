package com.shinhan.education.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data // 양방향이기 떄문에 toString 건드려야함 ❌
@Getter @Setter
@ToString(exclude = "board") @Entity @Table(name = "tbl_free_replies")
//게시물에서 toString -> 댓글 출력 -> 댓글의 toString -> 게시물 toString으로 게시물 출력... 의 무한반복으로 exclude="board"
@EqualsAndHashCode(of = {"rno"}) // 모두 같으면 같다.. 
@Builder @AllArgsConstructor @NoArgsConstructor
public class FreeBoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Lazy -> @Transactional
	private Long rno;
	private String reply;
	private String replyer;
	@CreationTimestamp
	private Timestamp regDate; // 대문자 -> 언더바( _ )로 명시
	@UpdateTimestamp
	private Timestamp updateDate;
	
	@ManyToOne // 연관관계 설정: n:1
	// FK: tbl_free_replies dp 칼럼이 board_bno 생성
	FreeBoard board;
}
