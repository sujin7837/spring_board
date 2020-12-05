package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.QuestionRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private QuestionRepository questionRepository;	//데이터베이스로부터 질문의 목록 받아오기
	
	@GetMapping("")
	public String name(Model model) {
		model.addAttribute("questions", questionRepository.findAll());	//questions라는 이름으로 model의 데이터를 담아서 전달
		return "index";
	}
}
