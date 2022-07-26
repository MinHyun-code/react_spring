package com.example.demo.login.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.dto.UserDto;
import com.example.demo.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LoginController {
	private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;
	
    // 로그인
    @RequestMapping(method = RequestMethod.POST, value = "/api/login")
    public UserDto login(@RequestParam String id, @RequestParam String pw){

    	UserDto login = new UserDto();
    	try {
        	String dbPw = loginService.selectPw(id);
        	if(passwordEncoder.matches(pw, dbPw)) {
        		login = loginService.mypageInfo(id);
        		login.setLogin_tf(true);
        	}
    	}
    	catch (Exception e) {
    		System.out.println(e);
		} 
		return login;
    }
    
    
    // 회원가입
    @RequestMapping(method = RequestMethod.POST, value = "/api/join")
    public void join(@RequestParam String id, @RequestParam String pw){
    	UserDto join = new UserDto();
    	join.setUser_id(id);
    	join.setUser_pw(passwordEncoder.encode(pw));
    	loginService.joinAction(join);
    }
    
    // 개인정보 조회
    @RequestMapping(method = RequestMethod.GET, value = "/api/mypage/{id}")
    public UserDto myInfo(@PathVariable String id) {
    	UserDto myInfo = new UserDto();
    	myInfo = loginService.mypageInfo(id);
    	return myInfo;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/api/mypage")
    public void myInfoUpdate(@RequestParam String id, @RequestParam String birth, @RequestParam String name, @RequestParam String pw){
    	UserDto myInfo = new UserDto();
    	myInfo.setUser_id(id);
    	myInfo.setUser_name(name);
    	myInfo.setUser_birth(birth);
    	myInfo.setUser_pw(passwordEncoder.encode(pw));
    	loginService.myInfoUpdate(myInfo);
    }
    
}