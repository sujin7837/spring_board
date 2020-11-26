package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller	//1. 이 클래스는 Controller 역할을 하는 클래스라는 것을 스프링에 알려줌
@RequestMapping("/questions")	//2. qna의 form.html과 연결해 줌(qna 전체를 대표하는 URL을 questions로 정해서 추가해줌)
public class QuestionController {
	@Autowired	//스프링 프레임워크가 관리하고 있는 인스턴스를 이 필드에 할당해달라고 요청
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")	//3. qna 파일의 form.html로 이동하게 해줌
	public String form(HttpSession session) {	//4. 인자로 세션을 받아옴
		if(!HttpSessionUtils.isLoginUser(session)) {	//4. 로그인한 사용자에 한해서만 글쓰기가 가능하게 함
			return "/users/longinForm";	//로그인하지 않은 경우에 로그인 페이지로 이동하도록 함
		}
		
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return "/users/longinForm";	
		}
		User sessionUser=HttpSessionUtils.getUserFromSession(session);	//6. 세션으로부터 user 정보를 얻어옴
		Question newQuestion=new Question(sessionUser, title, contents);	//Question을 만듦
		questionRepository.save(newQuestion);
		return "redirect:/";	//5. 제목과 내용이 정상적으로 전달되면 질문 목록(메인 페이지)으로 이동함 
	}
}
