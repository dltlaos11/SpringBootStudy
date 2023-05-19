package com.shinhan.education.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, String>{
	
}
