package com.shinhan.education.controller;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Controller
public class WebErrorController implements ErrorController {
	
	 private final ErrorAttributes errorAttributes;
	 public WebErrorController(ErrorAttributes errorAttributes){
		 this.errorAttributes = errorAttributes;
	 }
	 
	 public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
	 	   return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
	 }
	
	 //server.error.path: 기본path는 /error
	@GetMapping("/error")
	public String handleError(HttpServletRequest request, Model model, Exception ex) {
		// ex에서 err 출력이 안되서 request를 이용해 출력
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errPage="error/error500";
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				model.addAttribute("msg", "해당 페이지를 찾을 수 없습니다!!!");
				errPage = "error/error404";
			} else {
				WebRequest webRequest = new ServletWebRequest(request); // ⭕
		        Map<String, Object> errorRequest = getErrorAttributes(webRequest, false);
		        int errorStatus = (int) errorRequest.get("status");
		        String errorMessage =  errorRequest.get("error").toString();
		        System.out.println(errorRequest);
		        model.addAttribute("msg", "처리중 에러 발생!!!" 
		             + "errorStatus:" + errorStatus 
		             + "errorMessage:" +  errorMessage);
		        model.addAttribute("errorMap", errorRequest);
			}
		}
		return errPage;
		
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	}