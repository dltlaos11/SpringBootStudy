package com.shinhan.education.vo2;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "tbl_user")
public class UserVO {
	@Id
	@Column(name = "user_id")
	String userid;
	String username;
	
	// 주 테이블에서 참조하기
	@OneToOne // 1:1
	@JoinColumn(name = "phone_id")
	UserCellPhoneVO phone;
}
