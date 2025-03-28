import java.util.HashMap;

public class TrieNode
{
	private TrieData data = null;
	private boolean terminal = false;
	private int numChildren = 0;


	HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>(1, 0.75f);
	// Using a HashMap because it has very fast access, the cost of slow creation and high space usage.
	// the point of this is to search trees FAST, since if we're creating a Trie, we probably have the time to add stuff

	/**
	 * Lookup a child node of the current node that is associated with a
	 * particular character label.
	 *
	 * @param label The label to search for
	 * @return The child node associated with the provided label
	 */
	public TrieNode getChild(char label)
	{
		return children.get(label);
	}

	/**
	 * Get all the children of ths node.
	 *
	 * @return The HashMap of all the children
	 */
	public HashMap<Character, TrieNode> getChildren()
	{
		return children;
	}

	/**
	 * Add a child node to the current node, and associate it with the provided
	 * label.
	 *
	 * @param label The character label to associate the new child node with
	 * @param node  The new child node that is to be attached to the current node
	 */
	public void addChild(char label, TrieNode node)
	{
		numChildren++;
		children.put(label, node);
	}

	/**
	 * Add a child node to the current node, and associate it with the provided
	 * label.
	 *
	 * @param label The character label to associate the new child node with
	 */
	public void addChild(char label)
	{
		numChildren++;
		children.put(label, new TrieNode());
	}

	/**
	 * Return whether this node is terminal.
	 **/
	public boolean isTerminal()
	{
		return terminal;
	}

	public void setTerminal(boolean isTerminal)
	{
		terminal = isTerminal;
	}

	/**
	 * Add a new data object to the node.
	 *
	 * @param dataObject The data object to be added to the node.
	 */
	public void addData(TrieData dataObject)
	{
		data = dataObject;
	}

	/**
	 * The toString() method for the TrieNode that outputs in the format
	 * TrieNode; isTerminal=[true|false], data={toString of data}, #children={number of children}
	 * for example,
	 * TrieNode; isTerminal=true, data=3, #children=1
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return "TrieNode; isTerminal=" + terminal + ", data="+ ((data == null) ? "null" : data.toString()) + ", #children=" + numChildren;
	}
}

