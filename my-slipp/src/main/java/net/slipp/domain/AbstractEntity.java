package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass	//User, Answer, Question 등의 부모 클래스로 사용
@EntityListeners(AuditingEntityListener.class)	
//listener에서 해당하는 CreatDate와 LastModifiedDate라는 것을 인식해서, 변경사항이 발생하면 해당하는 날짜 시간을 자동으로 입력해주는 기능
public class AbstractEntity {
	/*1. @Id
	 * 	 private String userId;
	 * userId를 Primary Key로 사용
	 *2. @Id
	 *	 @Generated
	 *	 private Long id;
	 * Long이라는 새로운 값이 Primary Key를 관리할 수 있도록 추가해줌
	 * */
	@Id	//Database의 Primary Key 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//import.sql 파일에서 ID 값을 지정해주지 않으면 오류가 발생하는데, ID값을 자동 생성하도록 해주는 코드
	@JsonProperty	//getter 메소드를 이용할수도 있으나 대신 JsonProperty 어노테이션을 이용함
	private Long id;	//값이 자동으로 1씩 증가됨
	
	@CreatedDate
	private LocalDateTime createDate;	//게시물이 게시된 날짜 표시
	//LocalDateTime: Java8에서부터 지원
	
	@LastModifiedDate	//data에 시간 변동이 발생하면, 현재 시간으로 자동으로 업데이트를 해 줌
	private LocalDateTime modifiedDate;
	
	public Long getId() {
		return id;
	}

	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(id);
	}
	
	public String getFormattedCreateDate() {
		return getFormattedDate(createDate, "yyyy.MM.dd HH:mm:ss");
	}
	
	public String getFormattedModifiedDate() {
		return getFormattedDate(modifiedDate, "yyyy.MM.dd HH:mm:ss");
	}
	
	private String getFormattedDate(LocalDateTime dateTime, String format) {
		if(dateTime==null) {
			return "";
		}
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
}
