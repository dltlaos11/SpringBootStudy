package com.shinhan.education.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data // 양방향이기 떄문에 toString 건드려야함 ❌
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
@ToString @Entity @Table(name = "tbl_freeboards")
@EqualsAndHashCode(of = {"bno", "title"}) // 모두 같으면 같다.. 
public class FreeBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bno;
	private String title;
	private String writer;
	private String content;
	@CreationTimestamp
	private Timestamp regDate; // 대문자 -> 언더바( _ )로 명시
	@UpdateTimestamp
	private Timestamp updateDate;
	
	// 연관관계 설정: 1:n
	@JsonIgnore // jackson이 json 만들떄 toString 무시
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
			mappedBy = "board") // casacade, PK쪽에서 mappedBy로 자신이 다른객체에 매여있음을 명시
	List<FreeBoardReply> replies;
}
