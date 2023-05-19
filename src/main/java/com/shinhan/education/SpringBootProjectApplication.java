package com.shinhan.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Project 생성시 패키지의 하위에 있는 패키지는 자동스캔된다. SpringBoot가 알아서 해준다.
//@ComponentScan(basePackages = {"com.shinhan"}) 
//@EntityScan("com.shinhan")
//@EnableJpaRepositories("com.shinhan") // JpaRepository활성화 SpringBoot가 자동으로 해줌
/* 
 * com.shinhan.education; 
 * com.shinhan.education.vo;
 * com.shinhan.education.repository;
 * 			...
 * */
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
