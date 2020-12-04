package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity {	
	@ManyToOne	//answer와 user의 관계가 다 대 일
	@JoinColumn(foreignKey=@ForeignKey(name="fk_answer_writer"))
	@JsonProperty
	private User writer;
	
	@ManyToOne	//answer와 question의 관계가 다 대 일
	@JoinColumn(foreignKey=@ForeignKey(name="fk_answer_to_question"))
	@JsonProperty
	private Question question;
	
	@Lob
	@JsonProperty
	private String contents;
	
	public Answer() {}
	
	public Answer(User writer, Question question, String contents) {
		this.writer=writer;
		this.question=question;	//답변이 어떤 question에서 왔는지를 알려줌
		this.contents=contents;
	}
	
	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return loginUser.equals(this.writer);
	}

	@Override
	public String toString() {
		return "Answer [" + super.toString() + ", writer=" + writer + ", contents=" + contents + "]";
	}

	
	
}
