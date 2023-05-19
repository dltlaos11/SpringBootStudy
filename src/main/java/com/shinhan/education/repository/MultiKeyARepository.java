package com.shinhan.education.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo2.MultiKeyA;
import com.shinhan.education.vo2.MultiKeyAUsing;

public interface MultiKeyARepository extends CrudRepository<MultiKeyAUsing, MultiKeyA>{
	// 복합키여서 MultiKeyA	
}
