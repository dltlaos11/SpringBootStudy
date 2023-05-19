package com.shinhan.education.vo2;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.shinhan.education.vo.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "tbl_enum")
public class EnumTypeVO {
	@Id
	String mid;
	String mpassword;
	String mname;
	
	@ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER) // 즉시로딩..
	@CollectionTable(name = "tbl_enum_mroles", joinColumns = @JoinColumn(name="mid")) // mid와 join
	@Enumerated(EnumType.STRING) // 0 ,1, 2
	Set<MemberRole> mrole;
}
// 한명의 member(EnumTypeVO)가 여러개의 권한을 가지고 있다.
// tbl_enum_mroles에 member의 권한들이 저장(mid, morole) mid에 대해 여러개의 mrole이존재
