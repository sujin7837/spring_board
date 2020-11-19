package net.slipp.web;

import javax.servlet.http.HttpSession;

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
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {	//쿠키와 세션 중 세션에 로그인 한 사용자의 정보 저장 
		User user=userRepository.findByUserId(userId);
		//데이터베이스에 회원 정보가 존재해야 함
		//기본키는 Id이지만 userId를 기반으로 조회할 수 있음->UserRepository.java에 코드 추가
		if(user==null) {
			System.out.println("Login Success!");	//정상적으로 동작하는지 확인하기 위해 콘솔에 문구를 찍어보기 위한 코드 
			return "redirect:/users/loginForm";
		}
		if(!user.matchPassword(password)) {	
			//user가 가지고 있는 패스워드 정보를 계속 get으로 꺼내오는 것이 아니라, User에게 메시지를 보내서 비밀번호가 일치하는지 확인함 
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login Success!");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);	//세션에 현재 로그인한 사용자의 정보를 저장 
		
		return "redirect:/";	//로그인 성공시 메인 페이지로 이동하도록 해줌 
	}
	
	@GetMapping("/logout")	//세션에 정보를 한번 저장해놓으면 여러 웹페이지로 이동하더라도 세션에 계속 저장된 상태로 유지가 됨 
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		
		return "redirect:/";	//로그아웃을 할 때는 세션에 담겨있는 정보를 삭제하면 됨 
		
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
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {	
		//id를 받아와야 하므로 @PathVariable 사용
		//session을 통해서 사용자가 로그인을 했는지 여부를 알아옴 
		Object tempUser=session.getAttribute("sessionedUser");
		if(HttpSessionUtils.isLoginUser(session)) {	//로그인을 하지 않으면 로그인 페이지로 이동하도록 함 
			return "redirect:/users/loginForm";
		}
//자신의 정보만 수정할 수 있도록 하는 방법 1	
		User sessionedUser=HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)) {	//자신의 아이디와 일치하지 않는 경우에는 정보를 수정할 수 없도록 함 
			//User에게 메시지를 보내서 아이디가 일치하는지를 확인함
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
//자신의 정보만 수정할 수 있도록 하는 방법 2 
//findById() 괄호 안에 sessionedUser.getId()를 추가함 		
		User user=userRepository.findById(id).get();	//findOne(id) 오류-> findById(id).get()
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")	//개인정보수정
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		//실제 정보를 수정하는 부분에서도 예외 처리가 중요함 
		//해당 부분 중복 제거 
//		Object tempUser=session.getAttribute("sessionedUser");
//		if(tempUser==null) {	
//			return "redirect:/users/loginForm";
//		}	
		if(HttpSessionUtils.isLoginUser(session)) {	 
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser=HttpSessionUtils.getUserFromSession(session);
		if(!id.equals(sessionedUser.getId())) {	 
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user=userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";	//수정 후 사용자 목록으로 이동하여 잘 수정되었는지 확인
	}
}
