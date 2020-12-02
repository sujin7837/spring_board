package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@RestController //json으로 인식하게 하려면 Controller가 아니라 RestController로 바꿔주어야 
//@Controller
@RequestMapping("/api/questions/{questionId}/answers")	
//answer는 항상 question에 종속적이기 때문에 questions에서 questionId를 받고, 그 아래에 answers라는 URL을 만들어 줄 수 있음
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;	
	//Autowired를 통해 AnswerRepository를 가져와야지만 스프링 프레임워크가 answerRepository에 대한 인스턴스를 만들어서 할당해줌
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {	
			return null;	
		}
		
		User loginUser=HttpSessionUtils.getUserFromSession(session);
		Question question=questionRepository.findById(questionId).get();
		Answer answer=new Answer(loginUser, question, contents);	//answer 객체를 생성함
		return answerRepository.save(answer);	//답변을 저장
		//answer를 새로 만들었을 때, save의 return 값은 그대로 answer가 됨 
//		return String.format("redirect:/questions/%d", questionId);	//%d: 숫자값
	}
}
