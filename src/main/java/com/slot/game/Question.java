package com.slot.game;

public class Question {
	
	String question;
	String choice1;
	String choice2;
	String choice3;
	String choice4;
	int answer;
	int level;
	
	public Question(String record) {
		String [] data = record.trim().split(",");
		if (data.length == 7) {
			level = Integer.parseInt(data[0]);
			question = data[1];
			choice1 = data[2];
			choice2 = data[3];
			choice3 = data[4];
			choice4 = data[5];
			answer = Integer.parseInt(data[6]);
		} else {
			level = -1;
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getChoice1() {
		return choice1;
	}
	
	public String getChoice2() {
		return choice2;
	}
	
	public String getChoice3() {
		return choice3;
	}
	
	public String getChoice4() {
		return choice4;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public String getImage() {
		if (level == 1) {
			return "DSC_0053.jpg";
		}
		if (level == 2) {
			return "DSC_0054.jpg";
		}
		if (level == 3) {
			return "DSC_0050.jpg";
		}
		if (level == 4) {
			return "DSC_0048.jpg";
		}
		return "DSC_0048.jpg";
	}
	
	public String getImageSize() {
		if (level == 4) {
			return "40";
		}
		return "80";
	}

}
