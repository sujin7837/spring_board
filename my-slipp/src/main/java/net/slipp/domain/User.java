package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity	//Database와 매핑하는 어노테이션
public class User extends AbstractEntity{
	@Column(nullable=false, length=20, unique=true)	
	//userId 값이 널 값이 될 수 없도록 지정 
	//unique=true : userId는 유일해야 한다(똑같은 값이 들어올 수 없다) 
	@JsonProperty
	private String userId;
	
	@JsonIgnore	//명시적으로 json을 적용하지 않겠다고 표시해줌(없어도 됨)
	private String password;	//password는 json으로 받아오는 게 보안상 안전하지 않으므로 JsonProperty 어노테이션을 추가하지 않음
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;

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

	public void update(User newUser) {
		// TODO Auto-generated method stub
		this.password=newUser.password;
		this.email=newUser.email;
		this.name=newUser.name;
	}
	
	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(getId());
	}
	
	@Override
	public String toString() {
		return "User [" + super.toString() + ", userId=" + userId +  ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}	//id 대신 AbstractEntity에 toString 메소드를 추가하여 그것을 이용함

	
}
