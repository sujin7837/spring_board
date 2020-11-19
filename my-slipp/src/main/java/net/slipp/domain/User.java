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
	
	@Column(nullable=false, length=20)	//userId 값이 널 값이 될 수 없도록 지정 
	private String userId;
	
	private String password;
	private String name;
	private String email;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

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
