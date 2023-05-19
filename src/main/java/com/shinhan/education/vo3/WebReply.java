package com.shinhan.education.vo3;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (exclude ="board") // 무한루프
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_webreplies")
public class WebReply {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)//oracle:sequence, mysql:identity
	Long rno;
	String replyText;
	String replyer;
	@CreationTimestamp
	private Timestamp regdate;//yyyy-MM-dd hh:mm:ss
	@UpdateTimestamp  //생성시 생성일자, 수정시 변경된다. 
	private Timestamp updatedate;
	
	@JsonIgnore
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY) //lazy로 변경해도 OneToMany에서 Lazy가 그대로 수행~
	//@JoinColumn(name="board_bno") 같은 의미 ⭕
	WebBoard board; //board_bno
	//@ManyToOne, @OneToOne과 같이 @XXXToOne 어노테이션들은 기본이 즉시 로딩(EAGER) 이다.
 
}
