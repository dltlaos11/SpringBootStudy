package com.shinhan.education;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.UserCellPhoneVORepository;
import com.shinhan.education.repository.UserCellPhoneVORepository2;
import com.shinhan.education.repository.UserVO3Repository;
import com.shinhan.education.repository.UserVORepository;
import com.shinhan.education.vo2.UserCellPhoneVO;
import com.shinhan.education.vo2.UserCellPhoneVO2;
import com.shinhan.education.vo2.UserCellPhoneVO3;
import com.shinhan.education.vo2.UserVO;
import com.shinhan.education.vo2.UserVO2;
import com.shinhan.education.vo2.UserVO3;

@SpringBootTest
public class OneToOneTest {
	
	@Autowired
	UserCellPhoneVORepository pRepo;
	@Autowired
	UserVORepository uRepo;
	
	@Autowired
	UserCellPhoneVORepository2 uRepo2;
	
	@Autowired
	UserVO3Repository uRepo3;
	
	
	@Test
	void test3() {
		UserCellPhoneVO3 phone = UserCellPhoneVO3.builder()
				.phoneNumber("010-7259-2343 !!")
				.model("갤럭시10000 !!")
				.build();
		
		UserVO3 user = UserVO3.builder()
				.userid("zz !!")
				.username("Ju !!")
				.phone(phone)
				.build();
		
		phone.setUser(user); // 1:1 이므로 phone에서두 setUser
		uRepo3.save(user);
	}
//	@Test
	void test2() {
		UserVO2 user = UserVO2.builder()
				.userid("good")
				.username("홍대")
				.build();
		
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
				.phoneNumber("010-2413-2343")
				.model("아이폰10")
				.user(user)
				.build();
		
		uRepo2.save(phone);
	}
	
//	@Test
	void test1() {
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
				.phoneNumber("010-2413-2343")
				.model("아이폰10")
				.build();
		UserCellPhoneVO savedPhone = pRepo.save(phone); // 생성시 id자동 생성된다.
		
		UserVO user = UserVO.builder()
				.userid("good")
				.username("홍대")
				.phone(savedPhone)
				.build();
		
		uRepo.save(user);
	}
}
