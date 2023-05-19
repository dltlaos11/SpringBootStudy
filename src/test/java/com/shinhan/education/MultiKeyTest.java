package com.shinhan.education;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.MultiKeyARepository;
import com.shinhan.education.repository.MultiKeyBRepository;
import com.shinhan.education.vo2.MultiKeyA;
import com.shinhan.education.vo2.MultiKeyAUsing;
import com.shinhan.education.vo2.MultiKeyB;
import com.shinhan.education.vo2.MultiKeyBDTO;

@SpringBootTest
public class MultiKeyTest {
	
	@Autowired
	MultiKeyARepository aRepo;
	
	@Autowired
	MultiKeyBRepository bRepo;
	
	@Test
	void Test1() {
		MultiKeyAUsing a = new MultiKeyAUsing();
		a.setId1(2);
		a.setId2(4);
		a.setUserName("jin2 Add");
		a.setAddress("pusan Add");
		aRepo.save(a);
		
		MultiKeyA id = MultiKeyA.builder()
				.id1(5)
				.id2(6)
				.build();
		
//		MultiKeyAUsing a = new MultiKeyAUsing().builder()
//				.build();
	}
//	@Test
	void Test2() {
//		@Embeddable
		MultiKeyB id = MultiKeyB.builder()
				.id1(10)
				.id2(21)
				.build();
//		@EmbeddedId
		MultiKeyBDTO b = new MultiKeyBDTO().builder()
				.id(id)
				.userName("zzilre update")
				.address("함평 update")
				.build();
		bRepo.save(b);
	}
}
