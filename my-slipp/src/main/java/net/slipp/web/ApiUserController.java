package net.slipp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@RestController	//json과 같은 api를 개발하려면 Controller가 아니라 RestController가 필요함
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired	//database에서 사용자 정보를 조회해 옴
	private UserRepository userRepository;
	
	@GetMapping("/{id}")	//특정 id의 사용자의 상세보기를 하기 위해서 User 정보를 리턴함
	public User show(@PathVariable Long id) {
		return userRepository.findById(id).get();
	}
}
