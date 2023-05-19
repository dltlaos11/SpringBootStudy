package com.shinhan.education;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.JobsRepository;
import com.shinhan.education.vo.JobVO;

@SpringBootTest
public class JobTest {
	// findById
	@Autowired
	JobsRepository repo;
	
//	@Test
	public void test1() {
		String[] arr = {"마켓팅", "SI개발자", "SM개발자", "메니져", "AA", "BB","CC","DD","EE","FF"};
		// crud...create
		IntStream.range(0, arr.length).forEach(idx ->{
			JobVO job = JobVO.builder()
					.jobId("직책코드" + idx)
					.jobTitle(arr[idx])
					.minSalary(1000)
					.maxSalary(5000)
					.build();
			repo.save(job);
		});
	}
	
	@Test
	@Order(1)
	public void test2() {
		// crud...read
		Iterable<JobVO> datas = repo.findAll();
		List<JobVO> joblist = (List<JobVO>)datas;
		if(joblist.size() ==0) {
			System.out.println("조회데이터 없음");
		}else {
			for( JobVO job :joblist) {
				System.out.println(job);
			}
		}
	}
	@Test
	@Order(2)
	public void test3() {
		// crud...read
		// orElse-아니면, isPresent-있으면
		 Optional<JobVO> jobOptional = repo.findById("직책코드1x");
		 if(jobOptional.isPresent()) {
			 JobVO job = jobOptional.get();
			 System.out.println(job);
		 } else {
			 System.out.println("존재하는 직책이 없다");
		 }
		 JobVO job = repo.findById("직책코드1x").orElse(null);
		 if(job == null) {
			 System.out.println("존재하는 직책이 없다.");
		 }else {
			 System.out.println(job);
		 }
	}
//	@Test
	public void test4() {
		// crud...update
		repo.findById("직책코드1").ifPresent(job ->{
			job.setJobTitle("마켓팅-수정");
			job.setMaxSalary(100000);
//			job.setRegDate(regDate);
			repo.save(job);
		});
	}
//	@Test
	public void test5() {
		// crud...delete
		repo.findById("직책코드2").ifPresent(job ->{
			repo.delete(job);
		});
	}
//	@Test
	public void test6() {
		// crud...deleteAll
		repo.deleteAll();
	}

}
