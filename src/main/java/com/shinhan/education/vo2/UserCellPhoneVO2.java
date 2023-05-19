package com.shinhan.education.vo2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_usercellphone2")
public class UserCellPhoneVO2 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // 데이터저장시 자동 생성, 먼저 uservo보다 저장해야 함
	@Column(name = "phone_id")
	Long phoneId; // db에 _ 로 저장
	String phoneNumber;
	String model;
	
	// 대상테이블에서 참조하기.. 비식별자(key ❌)를 사용한 경우..
	// 이전에는 UserVO에서 참조
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	UserVO2 user;
}
