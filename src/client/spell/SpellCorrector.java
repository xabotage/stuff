package client.spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import client.spell.ITrie.INode;

public class SpellCorrector implements ISpellCorrector {
	
	private ITrie dict;
	
	public ITrie getDict() { return dict; }
	
	public SpellCorrector() {
		dict = new Trie();
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		Scanner scan = new Scanner(new File(dictionaryFileName));
		//scan.useDelimiter(Pattern.compile("\\p{javaWhitespace}+(#.*\n)*"));
		//System.out.println(scan.delimiter().toString());
		while(scan.hasNext()) {
			dict.add(scan.next());
		}
		scan.close();
		//System.out.println(dict.toString());
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		return null;
	}

	public List<String> getSimilarWords(String inputWord, List<String> wordBase) {
		dict = new Trie();
		Set<String> similarWords = new TreeSet<String>();
		List<String> returnWords = new ArrayList<String>();
		for(String word : wordBase) {
			dict.add(word);
		}

		if(inputWord.length() == 0) {
			return returnWords;
		}
		if(dict.find(inputWord) != null) {
			return returnWords;
		}

		ArrayList<String> dist1 = getDeletionWords(inputWord);
		dist1.addAll(getTransposeWords(inputWord));
		dist1.addAll(getAlterationWords(inputWord));
		dist1.addAll(getInsertionWords(inputWord));

		Collections.sort(dist1);
		INode foundNode;
		
		// first try with the distance 1 edits
		for(String d1 : dist1) {
			foundNode = dict.find(d1);
			if(foundNode != null) {
				similarWords.add(d1);
			}
		}
		
		ArrayList<String> dist2 = new ArrayList<String>();
		for(String d1w : dist1) {
			dist2.addAll(getDeletionWords(d1w));
			dist2.addAll(getTransposeWords(d1w));
			dist2.addAll(getAlterationWords(d1w));
			dist2.addAll(getInsertionWords(d1w));
		}

		Collections.sort(dist2);
		
		for(String d2 : dist2) {
			foundNode = dict.find(d2);
			if(foundNode != null) {
				similarWords.add(d2);
			}
		}
		
		returnWords.addAll(similarWords);
		return returnWords;
	}
	
	private ArrayList<String> getDeletionWords(String word) {
		StringBuilder sbword;
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0; i < word.length(); i++) {
			sbword = new StringBuilder(word);
			sbword.deleteCharAt(i);
			results.add(sbword.toString());
		}
		return results;
	}

	private ArrayList<String> getTransposeWords(String word) {
		ArrayList<String> results = new ArrayList<String>();
		char[] ca;
		char temp;
		for(int i = 0; i < word.length() - 1; i++) {
			ca = word.toCharArray();
			temp = ca[i];
			ca[i] = ca[i+1];
			ca[i+1] = temp;
			results.add(new String(ca));
		}
		return results;
	}

	private ArrayList<String> getAlterationWords(String word) {
		StringBuilder sbword;
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0; i < word.length(); i++) {
			for(char c = 'a'; c <= ('z' + 2); c++) {
				if(c == ' ' + 1)
					c = '-';
				if(c == 'z' + 1)
					c = ' ';
				sbword = new StringBuilder(word);
				sbword.replace(i, i+1, String.valueOf(c));
				results.add(sbword.toString());
				if(c == '-')
					break;
			}
		}
		return results;
	}
	
	private ArrayList<String> getInsertionWords(String word) {
		StringBuilder sbword;
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0; i <= word.length(); i++) {
			for(char c = 'a'; c <= ('z' + 2); c++) {
				if(c == ' ' + 1)
					c = '-';
				if(c == 'z' + 1)
					c = ' ';
				sbword = new StringBuilder(word);
				sbword.insert(i, c);
				results.add(sbword.toString());
				if(c == '-')
					break;
			}
		}
		return results;
	}
}
