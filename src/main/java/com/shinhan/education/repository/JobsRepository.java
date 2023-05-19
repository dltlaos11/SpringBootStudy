package com.shinhan.education.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.JobVO;

//interface설계... CRUD작업을 위해
//구현은 JPA가 한다.
//@Repository 안해도된다.
public interface JobsRepository extends CrudRepository<JobVO, String>{
	
}
