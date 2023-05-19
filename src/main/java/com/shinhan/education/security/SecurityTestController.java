package com.shinhan.education.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shinhan.education.repository.MemberRepository;
import com.shinhan.education.vo.MemberDTO;

import lombok.extern.java.Log;

@Controller
@Log
public class SecurityTestController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/guest")
	public String guest() {
		return "guest";
	}

	// post login은 만들지않음, spring security가 가로챔
	@GetMapping("/auth/login")
	public void login() {
	}

	@GetMapping("/loginSuccess")
	public void loginSuccess() {
	}

	@GetMapping("/accessDenied")
	public void accessDenied() {
	}

	@GetMapping("/logout")
	public void logout() {
	}

	@GetMapping("/auth/joinForm")
	public void joinFORM() {
	}

	@Autowired
	MemberRepository mrepo;
	@Autowired
	MemberService service;

	@PostMapping("/auth/joinProc") // 회원가입
	public String joinProc(@ModelAttribute MemberDTO member) {
		/*
		 * 415오류 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not
		 * supported
		 * 
		 * @RequestBody로 데이터 받을 때 이 오류가 남. Vo 만들어서 @ModelAttribute로 처리하니 된다.
		 */
		service.joinUser(member);
		return "redirect:/auth/login"; 
	}

	@GetMapping("/member2")
	public void member() {
		log.info("member 요청");
	}

	@GetMapping("/manager")
	public void forManager() {
		log.info("for manager 요청");
	}

	@GetMapping("/admin")
	public void forAdmin() {
		log.info("forAdmin 요청");
	}

	/*
	 * @Secured({ "ROLE_ADMIN" })
	 * 
	 * @RequestMapping("/adminSecret") public void forAdminSecret() {
	 * 
	 * log.info("admin secret ");
	 * 
	 * }
	 * 
	 * @Secured("ROLE_MANAGER")
	 * 
	 * @RequestMapping("/managerSecret") public void forManagerSecret() {
	 * 
	 * log.info("manager secret");
	 * 
	 * }
	 */
}
