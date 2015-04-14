package spell;

public class Node implements ITrie.INode {
	private int value;
	private Node[] nodes;
	
	public Node() {
		value = 0;
		nodes = new Node[26];
	}
	
	/**
	 * Recursively adds nodes to the structure.
	 * @param word The current state of the word yet to be added.
	 * @return An integer representing the total descendant nodes
	 * created by the operation.
	 */
	public int addWord(StringBuilder word) {
		if(word.length() == 0) {
			value++;
			return 0; // all done
		}
		int desc = 0;
		char c = word.charAt(0);
		word.deleteCharAt(0);
		if(nodes[c - 'a'] == null) {
			nodes[c - 'a'] = new Node();
			desc++;
		}
		return desc + nodes[c - 'a'].addWord(word);
	}
	
	public ITrie.INode findWord(StringBuilder word) {
		if(word.length() == 0) {
			if(this.value > 0)
				return this; // all done
			else
				return null; // not found
		}
		char c = word.charAt(0);
		word.deleteCharAt(0);
		if(nodes[c - 'a'] == null) {
			return null;
		}
		else {
			return nodes[c - 'a'].findWord(word);
		}
	}
	
	public void stringify(StringBuilder output, StringBuilder currentWord) {
		if(this.value > 0) {
			output.append(currentWord.toString());
			//output.append(this.value);
			output.append('\n');
		}

		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) {
				currentWord.append((char)(i + 'a'));
				//System.out.println(currentWord.toString());
				nodes[i].stringify(output, currentWord);
			}
		}
		if(currentWord.length() > 0)
			currentWord.deleteCharAt(currentWord.length() - 1);
	}

	/**
	 * Returns the frequency count for the word represented by the node
	 * 
	 * @return The frequency count for the word represented by the node
	 */
	@Override
	public int getValue() { return value; }
	
	public void setValue(int newValue) { value = newValue; }
	
	public Node[] getNodes() { return nodes; }
	
}
