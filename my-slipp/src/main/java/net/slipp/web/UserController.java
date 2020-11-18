package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")	//대표 URL을 설정해주어 모든 메소드에서 중복을 제거해줌 
public class UserController {
	//userRepository.findAll() 코드가 있으므로 List가 필요 없음
//	private List<User> users=new ArrayList<User>();
	
	//UserRepository를 직접 만들지 않고, 어딘가에 저장되어 있다고 생각하고 그것을 가져다 씀
	//UserRepository는 Spring Boot에서 알아서 생성해 줌
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginFrom() {
		return "/user/login";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")	//user에 post메소드를 받으면 새로운 사용자를 추가함
						//그런데 Get 방식에서 users를 사용하므로 맞춰주기 위해 users를 사용함
						//위에서 @RequestMapping을 사용했으므로 "users"를 생략(중복 제거)
	public String create(User user) {
		System.out.println("user : "+user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")	//여러명의 목록을 불러오는 것이므로 user가 아닌 users를 사용
					//위에서 @RequestMapping을 사용했으므로 "users"를 생략(중복 제거)
	public String List(Model model) {
		model.addAttribute("users", userRepository.findAll());	//데이터베이스에 있는 모든 데이터를 담아서 넣어줌
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {	//id를 받아와야 하므로 @PathVariable 사용
		User user=userRepository.findById(id).get();	//findOne(id) 오류-> findById(id).get()
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")	//개인정보수정
	public String update(@PathVariable Long id, User newUser) {
		User user=userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";	//수정 후 사용자 목록으로 이동하여 잘 수정되었는지 확인
	}
}
