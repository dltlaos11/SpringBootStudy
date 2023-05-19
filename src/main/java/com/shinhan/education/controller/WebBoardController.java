package com.shinhan.education.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.querydsl.core.types.Predicate;
import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.vo3.PageMarker;
import com.shinhan.education.vo3.PageVO;
import com.shinhan.education.vo3.WebBoard;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/webboard")
@ApiOperation(value = "게시판 등록화면", notes = "게시판 등록화면 !!")
public class WebBoardController {
	
	@Autowired
	WebBoardRepository boardRepo;
	
//	@Autowired
//	WebReplyRepository replyRepo;
	
	@PostMapping("/register.do")
	public String registerPost(WebBoard board, RedirectAttributes attr) {
		WebBoard newBoard = boardRepo.save(board);
		if(newBoard != null) {
			attr.addFlashAttribute("msg", "Insert OK");
		} else {
			attr.addFlashAttribute("msg", "Insert fail");
		}
		return "redirect:list.do"; // 페이지 정보 가져가기
	}
	@GetMapping("/register.do")
	public void registerGet() {
		
	}
	
	@PostMapping("/delete.do")
//	public String deletePost(WebBoard board, PageVO pageVO, RedirectAttributes attr) {
		public String deletePost(Long bno, PageVO pageVO, RedirectAttributes attr) { 
		
		// addFlashAttribute : 새로고침하면 없어짐(1회성)
		// addAttribute : 새로고침해도 유지, 둘다 list.do 페이지로 이동된다.
		
//		boardRepo.delete(board.getBno()); // 객체로 지우기
		boardRepo.deleteById(bno); // 번호로 지우기
		
		attr.addFlashAttribute("msg", "delete OK");
		attr.addAttribute("page", pageVO.getPage()); // 페이지 정보 명시
		attr.addAttribute("size", pageVO.getSize());
		attr.addAttribute("keyword", pageVO.getKeyword());
		attr.addAttribute("type", pageVO.getType());
		return "redirect:list.do"; // 페이지 정보 가져가기
	}
	
	@PostMapping("/modify.do")
	public String updatePost(WebBoard board, PageVO pageVO, RedirectAttributes attr) {
		WebBoard saveBoard = boardRepo.save(board);
		if(saveBoard == null) {
			attr.addFlashAttribute("msg", "Update Failed");
		} else {
			attr.addFlashAttribute("msg", "Update success");
		}
		attr.addAttribute("bno", board.getBno());
		attr.addAttribute("page", pageVO.getPage()); // 페이지 정보 명시
		attr.addAttribute("size", pageVO.getSize());
		attr.addAttribute("keyword", pageVO.getKeyword());
		attr.addAttribute("type", pageVO.getType());
//		System.out.println(board);
//		System.out.println(saveBoard);
		return "redirect:view.do";
	}
	
	@GetMapping("/modify.do")
	public void updateOrDelete(Long bno, Model model, PageVO pageVO) {
		boardRepo.findById(bno).ifPresent(bd ->{
			model.addAttribute("board", bd);
			model.addAttribute("pageVO", pageVO);
		});
	}
	
	@GetMapping("/view.do")
	public void selectById(Long bno, Model model, PageVO pageVO) {
		boardRepo.findById(bno).ifPresent(bd ->{
			model.addAttribute("board", bd);
			model.addAttribute("pageVO", pageVO);
		});
	}
	
	@GetMapping("/list.do")
	public void selectAll(PageVO pageVO, Model model, HttpServletRequest request) {
		if(pageVO == null) {
			pageVO = new PageVO();
			pageVO.setPage(1);
		} //pageVO= new PageVO(0, 10, "", "");
		
		/*
		 * Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); if
		 * (flashMap != null) { // flashMap에 추가한게 있다면 Object messsage =
		 * flashMap.get("msg"); System.out.println(messsage);
		 * model.addAttribute("resultMessage", messsage); }
		 */
		
		Predicate pre = boardRepo.makePredicate(pageVO.getType(), pageVO.getKeyword()); // 동적 sql

		Pageable paging = pageVO.makePageable(pageVO.getPage(), "bno");
//		Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		Page<WebBoard> result = boardRepo.findAll(pre, paging); // page
//		List<WebBoard> blist = result.getContent();
		
//		System.out.println("전체 페이지수: " + result.getTotalPages());
//		System.out.println("전체 건수: " + result.getTotalElements());
		PageMarker<WebBoard> pageMaker = new PageMarker<>(result, pageVO.getSize());
		model.addAttribute("blist", pageMaker);
//		Page<WebBoard> p_result = pageMaker.getResult();
//		System.out.println(p_result.getContent());
	}

}
