import java.util.HashMap;

public class SuffixTrieNode
{
	SuffixTrieData data = new SuffixTrieData();

	int numChildren = 0;

	public SuffixTrieNode(int sentencePos, int characterPos)
	{
		addData(sentencePos, characterPos);
	}

	public int getNumChildren()
	{
		return numChildren;
	}

	boolean terminal = false;
	public boolean isTerminal()
	{
		return terminal;
	}
	public void setTerminal(boolean terminal)
	{
		this.terminal = terminal;
	}

	HashMap<Character, SuffixTrieNode> children = new HashMap<Character, SuffixTrieNode>(1, 0.75f);
	// Using a HashMap because it has very fast access, the cost of slow creation and high space usage.
	// the point of this is to search trees FAST, since if we're creating a Trie, we probably have the time to add stuff
	// TODO calculate how often the map is getting rehashed

	/**
	 * Get all the children of ths node.
	 *
	 * @return The HashMap of all the children
	 */
	public HashMap<Character, SuffixTrieNode> getChildren()
	{
		return children;
	}

	/**
	 * Lookup a child node of the current node that is associated with a
	 * particular character label.
	 *
	 * @param label The label to search for.
	 * @return The child node associated with the provided {@code label}, or {@code null} if the child does not exist.
	 */
	public SuffixTrieNode getChild(char label)
	{
		return children.get(label);
	}

	/**
	 * Add a child node to the current node, and associate it with the provide label.
	 *
	 * @param label The character label to associate the new child node with.
	 * @param sentencePos TODO
	 * @param characterPos TODO
	 * @return The new child node added.
	 */
	public int addChild(char label, int sentencePos, int characterPos)
	{
		numChildren++;

		SuffixTrieNode newChild = new SuffixTrieNode(sentencePos, characterPos);

		int hashSize = children.size();
		children.put(label, newChild);
		if (hashSize > children.size())
		{
			// TODO REHASH occured here
			hashSize = children.size();
		}

		return hashSize;
	}

	public void addData(int sentencePos, int characterPos)
	{
		data.addStartIndex(new SuffixIndex(sentencePos, characterPos));
	}

	public String toString()
	{
		return data.toString();
	}
}
