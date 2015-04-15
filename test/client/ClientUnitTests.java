package client;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;
import client.spell.*;

public class ClientUnitTests {
	private SpellCorrector spell;
	private List<String> possibleValues;

	@Before
	public void setup() {
		spell = new SpellCorrector();
		possibleValues = new ArrayList<String>();
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void testSingleEditDist() {		
		String inputWord = "foo";
		spell.getSimilarWords(inputWord, wordBase);
	}

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

