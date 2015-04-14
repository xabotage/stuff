package spell;

public class Trie implements ITrie {
	private int wordCount;
	private int nodeCount;
	private Node root;

	public Trie() {
		wordCount = 0;
		nodeCount = 1;
		root = new Node();
	}
	
	public INode getRoot() { return root; }

	@Override
	public void add(String word) {
		if(word.length() == 0) return; // no monkey business
		if(find(word) == null) { // didn't already exist 
			wordCount++;
		}
		StringBuilder sbword = new StringBuilder(word.toLowerCase());
		nodeCount += root.addWord(sbword);
	}
	
	/**
	 * Searches the trie for the specified word
	 * 
	 * @param word The word being searched for
	 * 
	 * @return A reference to the trie node that represents the word,
	 * 			or null if the word is not in the trie
	 */
	@Override
	public INode find(String word) {
		if(word.length() == 0) return root; // no monkey business
		StringBuilder sbword = new StringBuilder(word.toLowerCase());
		return root.findWord(sbword);
	}
	
	/**
	 * Returns the number of unique words in the trie
	 * 
	 * @return The number of unique words in the trie
	 */
	@Override
	public int getWordCount() {
		return wordCount;
	}
	
	/**
	 * Returns the number of nodes in the trie
	 * 
	 * @return The number of nodes in the trie
	 */
	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		StringBuilder currentWord = new StringBuilder();
		root.stringify(output, currentWord);
		// print all the nodes in the trie in alphabetical order.
		// traverse the trie. Every time you hit a node that represents a word,
		// add it to the output. Pass a stringbuilder down the recursion so that
		// it can just be appended to along the way.
		// One of the stringbuilders has the output, the other stringbuilder that you
		// pass in has the word that you are currently doing.
		// Every time you recurse, you append a character. Every time you pop back up, you
		// pop out that character like a stack.
		return output.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		// standard set of checks
		// then check word count and node count
		// then do the areEqual tandem traversing
		if(o == null)
			return false;
		if(!(o instanceof ITrie))
			return false;
		if(this.wordCount != ((ITrie)o).getWordCount())
			return false;
		if(this.nodeCount != ((ITrie)o).getNodeCount())
			return false;
		return this.areEqual(root, ((Trie)o).getRoot());
	}
	
	private boolean areEqual(INode n1, INode n2) {
		if(n1 == null && n2 == null)
			return true;
		else if(n1 == null || n2 == null) // both not null at this point
			return false;

		if(n1.getValue() != n2.getValue())
			return false;
		else {
			for(int i = 0; i < ((Node)n1).getNodes().length; i++) {
				if(!areEqual(((Node)n1).getNodes()[i], ((Node)n2).getNodes()[i])) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return (wordCount * 17) ^ nodeCount;
	}
	
	public boolean equals() {
		return false;
	}
}
