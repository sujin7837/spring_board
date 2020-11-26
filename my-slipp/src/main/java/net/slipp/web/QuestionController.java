package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {	//path에 있는 질문의 아이디를 가져옴
		model.addAttribute("question", questionRepository.findById(id).get());
		//Question question=questionRepository.findById(id).get();->위 문장과 같은 의미의 코드(대체 가능)
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")	// 상세 페이지를 수정할 때
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		//수정에 대한 보안 설정을 해주기 위해 세션에서 정보를 가져옴
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return "/users/longinForm";	
		}
		//로그인한 사용자가 글쓴이와 동일한지 확인하고, 같지 않으면 로그인 페이지로 이동
		User loginUser=HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return "/users/longinForm";	
		}
		
		User loginUser=HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";
		}
				
		question.update(title, contents);	//수정한 내용이 적용됨
		questionRepository.save(question);	//수정한 내용을 저장함
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return "/users/longinForm";	
		}
		
		User loginUser=HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";
		}
		
		questionRepository.deleteById(id);	//delete(): entity를 인자로 받아서 삭제함, deleteById(): id를 받아서 그 개체를 삭제함
		return "redirect:/";
	}
}
