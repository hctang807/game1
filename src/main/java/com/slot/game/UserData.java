package com.slot.game;

public class UserData {
	String username;
	long score;
	int questionIndex;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setScore(long score) {
		this.score = score;
	}
	
	public long getScore() {
		return this.score;
	}
	
	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}
	
	public int getQuestionIndex() {
		return this.questionIndex;
	}
}
