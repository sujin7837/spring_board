package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity	//질문 정보를 데이터베이스에 추가함(글쓴이, 제목, 내용)
public class Question {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@ManyToOne	//question과 user의 관계가 다 대 일
	@JoinColumn(foreignKey=@ForeignKey(name="fk_question_writer"))	//foreignKey의 이름을 지정함
	@JsonProperty
	private User writer;	//Question에서 User를 바라볼 수 있게 관계를 맺음(String이 아니라 User 객체와 바로 관계를 맺도록 함)	private String writer;
	
	@JsonProperty
	private String title;
	
	@Lob	//데이터베이스에 더 많은 양의 글자를 포함할 수 있게 해줌
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer=
	
	private LocalDateTime createDate;	//게시물이 게시된 날짜 표시
	//LocalDateTime: Java8에서부터 지원
	
	@OneToMany(mappedBy="question")	//question이 answer를 가지고 있도록 함
	//mappedBy="ManyToOne에서의 필드의 이름"
	@OrderBy("id DESC")	//answer의 id를 기준으로 오름차순 정리
	
	private List<Answer> answer;
	
	public Question() {};	//default 생성자
	
	public Question(User writer, String title, String contents) {	//글쓴이, 제목, 내용에 대한 생성자
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate=LocalDateTime.now();
	}
	
	public String getFormattedCreateDate() {
		if(createDate==null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void update(String title, String contents) {	//상세 페이지 수정시 새롭게 변경된 제목과 내용을 적용함
		this.title=title;
		this.contents=contents;
		
	}

	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return this.writer.equals(loginUser);
	}
	
}
