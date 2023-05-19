package com.shinhan.education.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
@Configuration  // 스프링 실행시 설정파일 읽기 위한 어노테이션 
@EnableSwagger2 //// Swagger2를 사용하겠다는 어노테이션 
public class SwaggerConfiguration {

	private String version = "V1";
	private String title = "SpringBoot Education API " + version;
	private String basePackage = "com.shinhan.education.controller"; // 대상
	@Bean
	public Docket api() {    
		List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		responseMessages.add(new ResponseMessageBuilder().code(200).message("OK !!!").build());
		responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생 !!!")
				.responseModel(new ModelRef("Error")).build());
		responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다 !!!").build());
		
		
		//Docket : Swagger 설정을 할 수 있게 도와주는 클래스
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName(version) //Docket Bean이 한 개일 경우 생략해도 상관없으나, 둘 이상일 경우 충돌을 방지해야 하므로 설정해줘야 한다
				.select()  //ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해준다,PathSelectors.any() 로 설정하면 패키지 안에 모든 API를 한번에 볼 수 있다
				.apis(RequestHandlerSelectors.basePackage(basePackage)) //api 스펙이 작성되어 있는 패키지를 지정한다
				.paths(postPaths()).build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);  //Swagger에서 제공해주는 기본 응답 코드 (200, 401, 403, 404)false로 설정하면 기본 응답코드에 대한 메시지를 제거해준다
				/*.globalResponseMessage(RequestMethod.GET,responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages);*/


				
	}
	
	private Predicate<String> postPaths() {
		//return PathSelectors.regex("/(webboard|replies|rest)/.*");
		return PathSelectors.ant("/**");
		  
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description(
				"<h3>API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"images/umbrella.jpg\" width=\"150\">")
				.contact(new Contact("jin", "http://localhost:8888/app/webboard/list.do", "wed0406@daum.com"))
				.license("JJ License").licenseUrl("").version("1.0").build();

	}
	
	// apis() : 컨트롤러가 존재하는 패키지를 basepackage로 지정하여, RequestMapping( GetMapping,
	// PostMapping ... )이 선언된 API를 문서화 함
	// paths : apis()로 선택되어진 API중 특정 path 조건에 맞는 API들을 다시 필터링하여 문서화

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	
	
	
	 
    
}
