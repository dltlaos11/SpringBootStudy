package com.shinhan.education.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinhan.education.repository.FreeBoardRepository;
import com.shinhan.education.repository.FreeRepliesRepository;

@Controller
public class FreeBoardController {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeRepliesRepository replyRepo;
	
	@GetMapping("/freeboard/eltest")
	public void freeboard3(Model dataScope, HttpSession session) {
		dataScope.addAttribute("boardList", boardRepo.findAll());
		dataScope.addAttribute("now", new Date());
		dataScope.addAttribute("price", 123456789);
		dataScope.addAttribute("title", "This is a 스파르타 ");
		dataScope.addAttribute("options", Arrays.asList("apple", "bannana", "kiwi"));
		session.setAttribute("userName", "jin정진");
	}
	
	@GetMapping("/freeboard/selectAll")
	public String freeboard2(Model dataScope) {
		dataScope.addAttribute("boardList", boardRepo.findAll());
		
		return "freeboard/list";
	}
	
	@GetMapping("/freeboard/detail")
	// @RequestParam("bno") 생략가능
	public void freeboard1(@RequestParam("bno") Long bno, Model model) {
		boardRepo.findById(bno).ifPresent(board ->{
			model.addAttribute("board",board);
		});
	}
	
	@GetMapping("/firstzone1")
	public void test1(Model model) {
		model.addAttribute("greeting", "하이~");
		model.addAttribute("company", "신한금융");
		// 요청의 이름과 페이지가 같은경우
	}
	
	@GetMapping("/firstzone2")
	public String test2(Model model) {
		model.addAttribute("greeting", "ok~");
		model.addAttribute("company", "국민금융");
		// 요청의 이름과 페이지가 다른경우
		return "firstzone1"; // templates/firstzone.html
	}
	
	@GetMapping("/aa/firstzone3")
	public void test3(Model model) {
		model.addAttribute("greeting", "!!!!ok~");
		model.addAttribute("company", "!!!!국민금융");
	}
}
