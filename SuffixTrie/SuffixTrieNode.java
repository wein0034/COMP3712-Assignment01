import java.util.HashMap;

public class SuffixTrieNode
{
	SuffixTrieData data = new SuffixTrieData();

	public SuffixTrieNode(int sentencePos, int characterPos)
	{
		addData(sentencePos, characterPos);
	}

	public SuffixTrieNode(SuffixTrieData newData)
	{
		for (SuffixIndex index : newData.getStartIndexes())
		{
			addData(index.getSentence(), index.getCharacter());
		}
	}

	public int getNumChildren()
	{
		return children.size();
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

	HashMap<String, SuffixTrieNode> children = new HashMap<>(1, 0.75f);
	// Using a HashMap because it has very fast access, the cost of slow creation and high space usage.
	// the point of this is to search trees FAST, since if we're creating a Trie, we probably have the time to add stuff
	// TODO calculate how often the map is getting rehashed

	/**
	 * Get all the children of ths node.
	 *
	 * @return The HashMap of all the children
	 */
	public HashMap<String, SuffixTrieNode> getChildren()
	{
		return children;
	}

	/**
	 * Lookup a child node of the current node exactly matches a particular label.
	 *
	 * @param label The label to search for.
	 * @return The child node associated with the provided {@code label}, or {@code null} if the child does not exist.
	 */
	public SuffixTrieNode getChildExact(String label)
	{
		return children.get(label);
	}

	/**
	 * Lookup a child node of the current node that has the same starting character as a particular label.
	 *
	 * @param label The label to search for.
	 * @return An object containing child, and the label that best matched the provided {@code label}, or {@code null} if the child does not exist.
	 */
	public SuffixTrieChild getChild(String label)
	{
		// check if there exists a child with the same label, and return it if it exists
		SuffixTrieNode child = children.get(label);
		if (child != null)
		{
			return new SuffixTrieChild(label, child);
		}

		// look for a child that has a label starting with the same character
		else
		{
			// check each child
			for (String childLabel : children.keySet())
			{
				// find a label with the same starting character
				// there shouldn't be two children with the same starting character on one node
				if (label.charAt(0) == childLabel.charAt(0))
				{
					// return the child with the same starting character
					return new SuffixTrieChild(childLabel, getChildExact(childLabel));
				}
			}
		}

		// if no match is found, return null
		return null;
	}

	/**
	 * Add a child node to the current node, and associate it with the provide label.
	 * If a child already exists with the same label/key, the node associated with it will be replaced.
	 *
	 * @param label        The character label to associate the new child node with.
	 * @param sentencePos  TODO
	 * @param characterPos TODO
	 */
	public void addChild(String label, int sentencePos, int characterPos)
	{
		SuffixTrieNode newChild = new SuffixTrieNode(sentencePos, characterPos);
		children.put(label, newChild);
	}

	/**
	 * Replace the key of a child with a new key, keeping the same value.
	 *
	 * @param oldLabel The current key of the child.
	 * @param newLabel The new key of the child.
	 */
	public void replaceNode(String oldLabel, String newLabel)
	{
		SuffixTrieNode oldChild = children.get(oldLabel);
		children.remove(oldLabel);
		children.put(newLabel, oldChild);
	}

	/**
	 * Replace the key of a child with a new key, replacing the value with a new node.
	 * <p>
	 * Effectively the same as removing the old node, and inserting a completely new node.
	 *
	 * @param oldLabel The current key of the child.
	 * @param newLabel The new key of the child.
	 * @param newChild the value assigned to the new key.
	 */
	public void replaceNode(String oldLabel, String newLabel, SuffixTrieNode newChild)
	{
		children.remove(oldLabel);
		children.put(newLabel, newChild);
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
