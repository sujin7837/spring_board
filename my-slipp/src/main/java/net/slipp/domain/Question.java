package net.slipp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity	//질문 정보를 데이터베이스에 추가함(글쓴이, 제목, 내용)
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
	private String writer;
	private String title;
	private String contents;
	
	public Question() {};	//default 생성자
	
	public Question(String writer, String title, String contents) {	//글쓴이, 제목, 내용에 대한 생성자
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
}
