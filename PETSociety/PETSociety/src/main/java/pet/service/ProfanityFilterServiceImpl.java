package pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.model.Dictionary;

@Service
public class ProfanityFilterServiceImpl implements ProfanityFilterService {
	
	
	Dictionary dict;
	
	@Autowired
	public ProfanityFilterServiceImpl(Dictionary dict) {
		this.dict = dict;
	}
	
	@Override
	public String run(String input) {
		StringBuilder aux = new StringBuilder();
		String words[] = input.split(" ");
		for(int i = 0; i < words.length; i++) {
			words[i] = dict.checkWord(words[i]);
			aux.append(words[i]+" ");
			
		}
		
//		String answer = Arrays.toString(words);
		String answer = aux.toString();
		
		return answer;
	}

}
