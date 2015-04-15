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
	public void testInsertions() {		
		String inputWord = "bar";
		possibleValues.add("bary");
		possibleValues.add("abar");
		possibleValues.add("bbar");
		possibleValues.add("bacr");
		possibleValues.add("abacr");
		possibleValues.add("bbaar");
		possibleValues.add("barry");
		possibleValues.add("barryc");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(7, similars.size());
	}

	@Test
	public void testDeletions() {		
		String inputWord = "bar";
		possibleValues.add("ba");
		possibleValues.add("br");
		possibleValues.add("ar");
		possibleValues.add("b");
		possibleValues.add("a");
		possibleValues.add("r");
		possibleValues.add("");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(6, similars.size());
	}

	@Test
	public void testAlterations() {		
		String inputWord = "barry";
		possibleValues.add("brary");
		possibleValues.add("baryr");
		possibleValues.add("abrry");
		possibleValues.add("arbry");
		possibleValues.add("brray");
		possibleValues.add("bayrr");
		possibleValues.add("yairb");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(6, similars.size());
	}

	@Test
	public void testReplacements() {		
		String inputWord = "barry";
		possibleValues.add("aarry");
		possibleValues.add("bbrry");
		possibleValues.add("bacry");
		possibleValues.add("bardy");
		possibleValues.add("barre");
		possibleValues.add("garrf");
		possibleValues.add("baaay");
		possibleValues.add("bcryy");
		possibleValues.add("iiriy");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(8, similars.size());
	}

	@Test
	public void testCombined() {		
		String inputWord = "barry";
		possibleValues.add("arry");
		possibleValues.add("bbry");
		possibleValues.add("bacry");
		possibleValues.add("brdy");
		possibleValues.add("barer");
		possibleValues.add("aryr");
		possibleValues.add("abaray");
		possibleValues.add("bcryy");
		possibleValues.add("iiriy");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(8, similars.size());
	}

	@Test
	public void testSpaces() {		
		String inputWord = "foo";
		possibleValues.add("foo ");
		possibleValues.add("fo o");
		possibleValues.add(" foo");
		possibleValues.add("f o");
		possibleValues.add("f  ");
		possibleValues.add(" o ");
		possibleValues.add("  o");
		possibleValues.add(" f oo");
		possibleValues.add("fo  o");
		possibleValues.add("foo  ");
		possibleValues.add("f  oo  ");
		possibleValues.add(" f oo  ");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(10, similars.size());
	}

	@Test
	public void testHyphens() {		
		String inputWord = "foo";
		possibleValues.add("foo-");
		possibleValues.add("fo-o");
		possibleValues.add("-foo");
		possibleValues.add("f-o");
		possibleValues.add("f--");
		possibleValues.add("-o-");
		possibleValues.add("--o");
		possibleValues.add("-f-oo");
		possibleValues.add("fo--o");
		possibleValues.add("foo--");
		possibleValues.add("f--oo--");
		possibleValues.add("-f-oo--");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(10, similars.size());
	}

	@Test
	public void testSpacesAndHyphens() {		
		String inputWord = "foo";
		possibleValues.add("f -");
		possibleValues.add("f- ");
		possibleValues.add(" o-");
		possibleValues.add("-o ");
		possibleValues.add("- o");
		possibleValues.add(" f-oo");
		possibleValues.add("fo -o");
		possibleValues.add("foo- ");
		possibleValues.add("f- oo -");
		possibleValues.add("-f-oo--");
		List<String> similars = spell.getSimilarWords(inputWord, possibleValues);
		assertEquals(8, similars.size());
	}

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

