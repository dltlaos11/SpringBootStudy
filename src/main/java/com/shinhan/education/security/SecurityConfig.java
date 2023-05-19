package com.shinhan.education.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity // security설정을 담당하는 Bean이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// @Component : Bean 자동 생성, class level 작성
	// @Bean: 직접생성, function level에서 작성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Spring Security에서 제공하는 비밀번호 암호화 객체
	}

	@Override // WebSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성
	public void configure(WebSecurity web) throws Exception {
		// 파일 기준은 resources/static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/lib/**");
		// 해당 파일에서 재요청 가는 것을 막는다

	}

	protected void configure(HttpSecurity http) throws Exception {
		log.info("!!!!!!security config..........");
//		http.csrf().disable();
		http.csrf(); // request.getSession().getAttribute("_csrf"); csrf 토큰 생성
		// ****disable가 아니면 post,put, delete방식의 요청시 반드시 csrf 토큰을 가지고 post요청해야한다.
		// 1) antMatchers url 패턴에 대한 접근허용
		// 2) permitAll: 모든사용자가 접근가능하다는 의미
		// 3) hasRole : 특정권한을 가진 사람만 접근가능하다는 의미
		http.authorizeRequests() // HttpServletRequest에 따라 접근(access)을 제한
				.antMatchers("/error","/hello/**", "/auth/**", "/login/**", "/oauth2/**", "/h2-console/**", "/index").permitAll() // 로그인없이																															// 허용
				.antMatchers("/admin/**").hasRole("ADMIN") // /admin으로 시작하는 경로는 ADMIN롤을 가진 사용자만 접근 가능(자동으로 ROLE_가 삽입)
				.antMatchers("/manager/**").hasRole("MANAGER")
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.anyRequest().authenticated().and() //나머지요청은 인증된 사용자만 접근가능(반드시 로그인을 해야한다.),
				.formLogin() // form 기반으로 인증을 하도록 한다. 로그인 정보는 기본적으로 HttpSession을 이용
				.loginPage("/auth/login") // 로그인 페이지 링크 (post의 이름이 같다면 loginProcessingUrl생략)
				.loginProcessingUrl("/auth/login") //이름이 다르게 추가하면 controller에 인증구현 , 생략하거나 이름이 get과 같으면 자동인증처리  
				.defaultSuccessUrl("/loginSuccess"); //스프링시큐리티가 해당주소로 오는 요청을 가로채서 대신한다.
		
		http.logout() // 로그아웃에 관한 설정을 의미
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/auth/login") // 로그아웃 성공시
				.invalidateHttpSession(true); // 세션 지우기
		
		http.exceptionHandling().accessDeniedPage("/accessDenied");// 403 예외처리 핸들링 권한이 없는 대상이 접속을시도했을 때
	}
}

//csrf(크로스사이트 위조요청에 대한 설정) 토큰 비활성화 (test시에는 disable권장)
		//http.exceptionHandling().accessDeniedPage("/accessDenied"); // 403 예외처리 핸들링 권한이 없는 대상이 접속을시도했을 때
		// http.oauth2Login().userInfoEndpoint() // OAuth2 로그인 성공후 사용자정보를 가져오기 위함
		// .userService(customOAuth2UserService).and(); // 소셜로그인후 사용자정보 가져오기
//     .authorizationEndpoint()
//     .baseUri("/oauth2/authorization")
//      .and()   
//     .redirectionEndpoint()
//     .baseUri("/oauth2/callback/*")
//     .and();

		// .successHandler(oAuth2AuthenticationSuccessHandler)
		// .failureHandler(oAuth2AuthenticationFailureHandler);

		// 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// http.addFilterBefore(customAuthenticationFilter(),
		// UsernamePasswordAuthenticationFilter.class);

		// 인증되지않는 경우 ->401오류처리
		// http.exceptionHandling().authenticationEntryPoint(restAuthEntryPoint); //to
		// support REST
// 지금 사용자생성 postMapping에 가지않는 이유?
// .usernameParameter("username")
// .passwordParameter("password")
//.defaultSuccessUrl("/auth/loginSuccess") // 로그인 성공 후 리다이렉트 주소
