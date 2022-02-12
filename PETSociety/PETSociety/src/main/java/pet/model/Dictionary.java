package pet.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Dictionary {
	private List<String> badWords = new ArrayList<String>();
	
	public Dictionary(){
		setWords();
	}

	
	public Dictionary(List<String> badWord) {
		super();
		this.badWords = badWord;
		
	}
	
	private void setWords() {
		badWords.add("barnacles");
		badWords.add("bad_word");
		badWords.add("pineapple-pizza");
		badWords.add("inferno");
	}
	
	public String checkWord(String word) {
		if(badWords.contains(word)) {
			return "***";
		}
		return word;
	}
	
	
	
}
