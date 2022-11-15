package com.showmual.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.showmual.model.UserDto;
import com.showmual.service.UserService;
import com.showmual.validate.CheckUserIdValidator;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/closet")
public class UserController {
	
	private final UserService userService;
	private final CheckUserIdValidator checkUserIdValidator;
	
	/* 커스텀 유효성 검증을 위해 추가 */
	@InitBinder
	public void validatorBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(checkUserIdValidator);
	}
	
	// 메인 페이지
	@RequestMapping(value = {"/","/index",""})
	public String index() {
		return "index";
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String execSignup(@Valid UserDto userDto, Errors errors, Model model) {
		if (errors.hasErrors()) {

			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}

			return "login";
		}

		userService.joinUser(userDto);
		return "redirect:/closet/login";
	}
	
	// 아이디 중복체크
    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)
    @ResponseBody
    public Long idCheck(@RequestParam("id") String username) {
        
        Long cnt = userService.countByUsername(username);
        return cnt;
    }
	
    // 닉네임 중복체크
    @RequestMapping(value = "/nicknameCheck", method = RequestMethod.POST)
    @ResponseBody
    public Long nicknameCheck(@RequestParam("nickname") String nickname) {
        
        Long cnt = userService.countByNickname(nickname);
        return cnt;
    }
    
	// 로그인 페이지
	@GetMapping("/login")
	public String login() {
	    
		return "login";
	}

	// 접근 거부 페이지
	@GetMapping("/denied")
	public String dispDenied() {
	    
		return "denied";
	}

	// 내 정보 페이지
	@GetMapping("/myinfo")
	public String dispMyInfo() {
	    
		return "myinfo";
	}

	// Contact 페이지
	@RequestMapping("/contact")
	public String contact() {
		
		return "contact";
	}
	
	// 나만의 옷장 페이지
    @GetMapping("/closet")
    public String closet() {
        
        return "closet";
    }
    
    // 공지사항 페이지
    @GetMapping("/notice")
    public String notice() {
        
        return "notice";
    }
    
    // 공지사항 글 상세보기 페이지
    @GetMapping("/viewNotice")
    public String view() {
        
        return "viewNotice";
    }
    
    // 공지사항 글쓰기 페이지
    @GetMapping("/writeNotice")
    public String write() {
        
        return "writeNotice";
    }
    
	// 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
          
        return "admin";
    }
	
    // 테스트 페이지
    @GetMapping("/test")
    public String test() {
          
        return "test";
    }
    
}
