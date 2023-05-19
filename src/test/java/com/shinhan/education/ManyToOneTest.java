package com.shinhan.education;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.MemberRepository;
import com.shinhan.education.repository.ProfileRepository;
import com.shinhan.education.vo.MemberDTO;
import com.shinhan.education.vo.MemberRole;
import com.shinhan.education.vo.ProfileDTO;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class ManyToOneTest {
	
	@Autowired
	MemberRepository mRepo;
	@Autowired
	ProfileRepository pRepo;

//	@Test
	// 멤버의 profile 갯수얻기
	public void getProfileCount() {
		List<Object []> result = pRepo.getMemberWithProfileCount("user");
		result.forEach(arr -> log.info(Arrays.toString(arr)+"DW"));
	}
	
//	@Test
	void addMember() {
		IntStream.range(1, 4).forEach(i -> {
			MemberDTO mem = MemberDTO.builder()
					.mid("manager-"+i)
					.mname("매니저"+i)
					.mpassword("8888")
					.mrole(MemberRole.MANAGER)
					.build();
			mRepo.save(mem);
		});
	}
	
	// 해당 profile의 Member정보 알아내기
//	@Test
	void getMemberByProfile() {
		Long pno = 405L;
		pRepo.findById(pno).ifPresent(p->{
			MemberDTO member = p.getMember();
			log.info(p.isCurrentYn()+ " : " + member.getMname()+ " _ - _ - _ - _"+ member.getMrole());
		});
	}
	
	// 특정멤버의 profile조회하기
//	@Test
	void getProfileByMember() {

		MemberDTO member = mRepo.findById("user1").orElse(null);
		
		pRepo.findByMember(member).forEach(pf ->{
			log.info(pf.toString());
		});
	}
	
//	@Test
	void profileInsertTest() {
		// 'user1'의 profile5건 입력
		// 'user2'의 profile5건 입력
		MemberDTO member = mRepo.findById("user1").orElse(null);
		MemberDTO member2 = mRepo.findById("user2").orElse(null);
//		if(member !=null) {
//			log.info(member.toString());
//			IntStream.range(1, 6).forEach(i->{
//				ProfileDTO profile = ProfileDTO.builder()
//						.fname("face-" + i+".jpg")
//						.currentYn(i==5?true:false)
//						.member(member) // memId
//						.build();
//				pRepo.save(profile);
//			});
//			pRepo.findAll().forEach(profile ->log.info(profile.toString()));
//		}
		if(member2 !=null) {
			log.info(member2.toString());
			IntStream.range(1, 6).forEach(i->{
				ProfileDTO profile = ProfileDTO.builder()
						.fname("hair-" + i+".jpg")
						.currentYn(i==5?true:false)
						.member(member2) // memId JOIN
						.build();
				pRepo.save(profile);
			});
			pRepo.findAll().forEach(profile ->log.info(profile.toString()));
		}
	}
	
//	@Test
	void memberSelectAll() {
		mRepo.findAll().forEach(member -> {
			log.info(member.toString());
		});
	}
	
//	@Test
	void memberInsert() {
		// member table에 10명 입력하기
		IntStream.rangeClosed(1, 10).forEach(i -> {
			MemberDTO member = MemberDTO.builder()
					.mid("user"+i)
					.mname("멤버" + i)
					.mpassword("1234")
					.mrole(MemberRole.USER)
					.build();
			if(i<=5) {
				member.setMrole(MemberRole.ADMIN);
			} else {
				member.setMrole(MemberRole.USER);
			}
			mRepo.save(member);
		});	
	}
 }
