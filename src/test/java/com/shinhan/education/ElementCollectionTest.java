package com.shinhan.education;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.EnumTypeVORepository;
import com.shinhan.education.vo.MemberRole;
import com.shinhan.education.vo2.EnumTypeVO;

@SpringBootTest
public class ElementCollectionTest {
	
	@Autowired
	EnumTypeVORepository repo;
	
	@Test
	void test3() {
		repo.findById("hee").ifPresent( m-> {
			System.out.println(m);
			for(MemberRole role : m.getMrole()) {
				System.out.println(role.name());
			}
		});
	}
	
//	@Test
	void test2() {
		repo.findAll().forEach(m -> {
			System.out.println(m);
		});
	}
	
//	@Test
	void test1() {
		
		Set<MemberRole> mrole = new HashSet<>();
		mrole.add(MemberRole.ADMIN);
		mrole.add(MemberRole.USER);
		mrole.add(MemberRole.MANAGER);
		
		EnumTypeVO vo = EnumTypeVO.builder()
				.mid("hee")
				.mname("채희")
				.mpassword("1234")
				.mrole(mrole)
				.build();
		repo.save(vo);
	}
}
