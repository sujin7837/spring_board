package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity	//Database와 매핑하는 어노테이션
public class User {
	/*1. @Id
	 * 	 private String userId;
	 * userId를 Primary Key로 사용
	 *2. @Id
	 *	 @Generated
	 *	 private Long id;
	 * Long이라는 새로운 값이 Primary Key를 관리할 수 있도록 추가해줌
	 * */
	@Id	//Database의 Primary Key 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//import.sql 파일에서 ID 값을 지정해주지 않으면 오류가 발생하는데, ID값을 자동 생성하도록 해주는 코
	private Long id;	//값이 자동으로 1씩 증가됨
	
	@Column(nullable=false, length=20, unique=true)	
	//userId 값이 널 값이 될 수 없도록 지정 
	//unique=true : userId는 유일해야 한다(똑같은 값이 들어올 수 없다) 
	private String userId;
	
	private String password;
	private String name;
	private String email;
	
	public Long getId() {
		return id;
	}
	
	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(id);
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean matchPassword(String newPassword) {
		if(newPassword==null) {
			return false;
		}
		return newPassword.equals(password);	//입력한 패스워드가 기존에 지정한 패스워드와 같으면 로그인 성공 
	}
	
//메시지를 통해 패스워드를 전달(matchPassword 이용)하면 해당 코드가 필요 없어짐 
//	public String getPassword() {
//		return password;
//	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	public void update(User newUser) {
		// TODO Auto-generated method stub
		this.password=newUser.password;
		this.email=newUser.email;
		this.name=newUser.name;
	}

	
}
