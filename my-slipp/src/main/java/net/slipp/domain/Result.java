package net.slipp.domain;

//제대로 동작하는지 확인하기 위한 클래스
public class Result {
	private boolean valid;	//Exception이 발생했는지 여부를 가지고 있는 속성
	
	private String errorMessage;
	
	private Result(boolean valid, String errorMessage) {
		this.valid=valid;
		this.errorMessage=errorMessage;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
}
