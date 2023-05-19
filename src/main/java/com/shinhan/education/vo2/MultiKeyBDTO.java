package com.shinhan.education.vo2;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // jpa가 관리
@Table(name = "tbl_child2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiKeyBDTO {
	@EmbeddedId
	MultiKeyB id;
	
	private String userName;
	private String address;	
}

