package com.shinhan.education.security;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.education.repository.MemberRepository;
import com.shinhan.education.vo.MemberDTO;

@Service
public class MemberService implements UserDetailsService {
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MemberRepository mrepo; // 회원가입 저장

	@Autowired
	PasswordEncoder passwordEncoder; // security config에서 Bean생성

	// 회원가입
	//@Transactional
	public MemberDTO joinUser(MemberDTO member) {
        // 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		// mpassword: 1234 => encode하면 암호화
		String pwd = passwordEncoder.encode(member.getMpassword());
		member.setMpassword(pwd);
		System.out.println("암호화된 pass:" + pwd);
		return mrepo.save(member);
	}

    //!!!!반드시 구현해야한다.
	// sf에서 제공하는 UserDetails형태에 맞게 변형하는 과정, 인증 과정
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid:" + mid);
		//filter는 조건에 맞는것만 선택
		//map: 변형한다. 

		UserDetails member = mrepo.findById(mid).filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		System.out.println("UserDetails member:" + member);
		httpSession.setAttribute("user", member);
		httpSession.setAttribute("member", mrepo.findById(mid).get());
		return member;
	}

}
