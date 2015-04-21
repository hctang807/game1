package com.slot.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameService {
	
	ArrayList<Question> questionList = null;
	
	public GameService() {
	}	

	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public String loginPage(Model model, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		loadQuestion(request);
        	return "loginPage";
	}

	@RequestMapping(value="/verifyLogin", method=RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.OK)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response, String username) throws InterruptedException {
		loadQuestion(request);
		UserData user = loadUserData(request, username);
		model.addAttribute("username", username);
		if (user.getQuestionIndex() == -1) {
			return "redirect:showLevel";
		} else {
			return "redirect:showQuestion";
		}
	}

	@RequestMapping(value="/selectLevel", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public String selectLevel(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("level") String level) throws InterruptedException {
		loadQuestion(request);
		UserData user = loadUserData(request, username);
		model.addAttribute("username", username);
		int ilevel = Integer.parseInt(level);
		int questionIndex = -1;
		int startIndex = 0;
		if (user.getQuestionIndex() != -1) {
			startIndex = user.getQuestionIndex();
		}
		Random random = new Random(System.currentTimeMillis());
		int roll = random.nextInt(questionList.size());
		
		for (int i = startIndex + 1 ; i != startIndex ; i++) {
			if (i == questionList.size()) {
				i = 0;
			}
			Question question = questionList.get(i);
			if (question.getLevel() == ilevel) {
				questionIndex = i;
				if (roll == 0) {
					break;
				} else {
					roll--;
				}
			}
		}
		user.setQuestionIndex(questionIndex);
		saveUserData(request, user);
		return "redirect:showQuestion";
	}
	
	@RequestMapping(value="/showLevel", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public String showLevel(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username) throws InterruptedException {
		loadQuestion(request);
		UserData user = loadUserData(request, username);
		model.addAttribute("username", username);
		model.addAttribute("score", user.getScore());
		return "levelPage";
	}
	
	@RequestMapping(value="/showQuestion", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public String showQuestion(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username) throws InterruptedException {
		loadQuestion(request);
		UserData user = loadUserData(request, username);
		model.addAttribute("username", username);
		int questionIndex = user.getQuestionIndex();
		if (questionIndex == -1 || questionIndex >= questionList.size()) {
			return "redirect:showLevel";
		}
		Question question = questionList.get(questionIndex);
		model.addAttribute("question", question.getQuestion());
		model.addAttribute("answer1", question.getChoice1());
		model.addAttribute("answer2", question.getChoice2());
		model.addAttribute("answer3", question.getChoice3());
		model.addAttribute("answer4", question.getChoice4());
		model.addAttribute("image_file", question.getImage());
		model.addAttribute("image_size", question.getImageSize());
        return "questionPage";
	}
	
	@RequestMapping(value="/verifyAnswer", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public String verifyAnswer(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("answer") String answer) throws InterruptedException {
		loadQuestion(request);
		int ianswer = Integer.parseInt(answer);
		UserData user = loadUserData(request, username);
		model.addAttribute("username", username);
		int questionIndex = user.getQuestionIndex();
		Question question = questionList.get(questionIndex);
		if (question.getAnswer() == ianswer) {
			System.out.println("Correct");
			user.setScore(user.getScore() + 100);
		} else {
			System.out.println("Incorrect");
			user.setScore(user.getScore() * 3 / 4);
		}
		user.setQuestionIndex(-1);
		saveUserData(request, user);
		return "redirect:showLevel";
	}
	
	private String getFilePath(HttpServletRequest request, String resource) {
		request.getSession().getServletContext().getRealPath(resource);
    	String path = request.getSession().getServletContext().getRealPath("/WEB-INF" + resource);
		return path;
	}
	
	private synchronized void loadQuestion(HttpServletRequest request) {
		if (questionList == null) {
	        String content = "";
	        try {
	        	DataInputStream in = new DataInputStream(new FileInputStream(getFilePath(request, "/data/question.dat")));
	        	BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	        	String strLine;
	        	questionList = new ArrayList<Question>();
	        	while ((strLine = br.readLine()) != null) {
	        		if (strLine.startsWith("#") == false) {
	        			Question question = new Question(strLine);
	        			if (question.getLevel() != -1) {
	        				questionList.add(question);
	        			}
	        		}        		
	        	}
	        	in.close();
	        }
	        catch (Exception e) { 
				System.out.println("loadQuestion - " + e.getMessage());
	        }
	        System.out.println("Number of questions : " + questionList.size());
		}
	}
	
	private void saveUserData(HttpServletRequest request, UserData user) {
		try{
			FileWriter fstream = new FileWriter(getFilePath(request, "/data/" + user.getUsername()));
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(user.getScore() + "\n");
			out.write(user.getQuestionIndex() + "\n");
			out.close();
		} catch (Exception e) {
			System.out.println("saveUserData - " + e.getMessage());
		}
	}
	
	private UserData loadUserData(HttpServletRequest request, String username) {
		UserData user = new UserData();
		int line = 0;
		long score = 0;
		int questionIndex = -1;
		File file = new File(getFilePath(request, "/data/" + username));
		if (file.exists()) {
			try {
	        	DataInputStream in = new DataInputStream(new FileInputStream(file));
	        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	String strLine;
	        	while ((strLine = br.readLine()) != null) {
	        		if (strLine.startsWith("#") == false) {
	        			if (line == 0) {
	        				score = Long.parseLong(strLine);
	        			}
	        			if (line == 1) {
	        				questionIndex = Integer.parseInt(strLine);
	        			}
	        			line++;
	        		}
	        	}
			} catch (Exception e) {
				System.out.println("loadUserData - " + e.getMessage());
			}
		}
		user.setUsername(username);
		user.setScore(score);
		user.setQuestionIndex(questionIndex);
		return user;		
	}

}
