package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")	
//answer는 항상 question에 종속적이기 때문에 questions에서 questionId를 받고, 그 아래에 answers라는 URL을 만들어 줄 수 있음
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;	
	//Autowired를 통해 AnswerRepository를 가져와야지만 스프링 프레임워크가 answerRepository에 대한 인스턴스를 만들어서 할당해줌
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return "/users/login";	
		}
		
		User loginUser=HttpSessionUtils.getUserFromSession(session);
		Question question=questionRepository.findById(questionId).get();
		Answer answer=new Answer(loginUser, question, contents);	//answer 객체를 생성함
		answerRepository.save(answer);	//답변을 저장
		return String.format("redirect:/questions/%d", questionId);	//%d: 숫자값
	}
}
