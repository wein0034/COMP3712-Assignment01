import org.w3c.dom.Node;

import java.util.*;

public class Trie
{

	private TrieNode root = new TrieNode();

	/**
	 * Inserts a string into the trie and returns the last node that was
	 * inserted.
	 *
	 * @param str  The string to insert into the trie
	 * @param data The data associated with the string
	 * @return The last node that was inserted into the trie
	 */
	public TrieNode insert(String str, TrieData data)
	{
		char[] chars = str.toCharArray();
		TrieNode currentNode = root;

		boolean add = false;
		for(char c : chars)
		{
			if (!add)
			{
				if (currentNode.getChild(c) != null)
				{
					// search through the tree as long as the current string already exists
					currentNode = currentNode.getChild(c);
				}
				else
				{
					// when a missing char is found, add that letter and switch add to true so it skips searching
					currentNode.addChild(c);
					currentNode = currentNode.getChild(c);
					add = true;
				}
			}
			else
			{
				// add all remaining
				currentNode.addChild(c);
				currentNode = currentNode.getChild(c);
			}
		}

		// add the data for the current string to the last node
		currentNode.addData(data);

		// set the current node to be terminal
		currentNode.setTerminal(true);

		return currentNode;
	}

	/**
	 * Search for a particular prefix in the trie, and return the final node in
	 * the path from root to the end of the string, i.e. the node corresponding
	 * to the final character.
	 * <p>
	 * getNode() differs from get() in that getNode()
	 * searches for any prefix starting from the root, and returns the node
	 * corresponding to the final character of the prefix, whereas get() will
	 * search for a whole word only and will return null if it finds the pattern
	 * in the trie, but not as a whole word.  A "whole word" is a path in the
	 * trie that has an ending node that is a terminal node.
	 *
	 * @param str The string to search for
	 * @return the final node in the path from root to the end of the prefix, or
	 * null if prefix is not found
	 */
	public TrieNode getNode(String str)
	{
		char[] chars = str.toCharArray();
		TrieNode currentNode = root;

		for(char c : chars)
		{
			if (currentNode.getChild(c) != null)
			{
				// search through the tree as long as the current string already exists
				currentNode = currentNode.getChild(c);
			}
			else
			{
				// when a missing char is found, the string doesn't exist, so return null
				return null;
			}
		}

		return currentNode;
	}

	/**
	 * Searches for a word in the trie, and returns the final node in the search
	 * sequence from the root, i.e. the node corresponding to the final
	 * character in the word.
	 * <p>
	 * getNode() differs from get() in that getNode() searches for any prefix
	 * starting from the root, and returns the node corresponding to the final
	 * character of the prefix, whereas get() will search for a whole word only
	 * and will return null if it finds the pattern in the trie, but not as a
	 * whole word. A "whole word" is a path in the
	 * trie that has an ending node that is a terminal node.
	 *
	 * @param str The word to search for
	 * @return The node corresponding to the final character in the word, or
	 * null if word is not found
	 */
	public TrieNode get(String str)
	{
		TrieNode node = getNode(str);

		if ((node != null) && (node.isTerminal()))
		{
			return node;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retrieve from the trie an alphabetically sorted list of all words
	 * beginning with a particular prefix.
	 *
	 * @param prefix The prefix with which all words start.
	 * @return The list of words beginning with the prefix, or an empty list if
	 * the prefix was not found.
	 */
	public List<String> getAlphabeticalListWithPrefix(String prefix)
	{
		// get a list of all words following the prefix
		List<String> words = getAlphabeticalListFromNode(getNode(prefix), prefix);

		//sort the list alphabetically
		Collections.sort(words);

		//return the list
		return words;
	}

	/**
	 * Recursively search the trie, starting at a given node, to find every word that follows the given prefix.
	 *
	 * @param node The node to begin searching from.
	 * @param prefix The prefix with which all words start. Typically, the string required to reach the starting node.
	 * @return The list of words following the node, or an empty list if none are found.
	 */
	private List<String> getAlphabeticalListFromNode(TrieNode node, String prefix)
	{
		// create an empty list, which will be added to with each recursion.
		List<String> words = new ArrayList<>();

		// exit early if the current node is null
		if (node != null)
		{
			// if the current node is terminal, add the prefix required to reach it to the list of words.
			if (node.isTerminal())
			{
				words.add(prefix);
			}

			// recurse through all the children of the current node
			for (char child : node.getChildren().keySet() )
			{
				// add any words found by the children to the list of words
				words.addAll(getAlphabeticalListFromNode(node.getChild(child), prefix + child));
				// `prefix: prefix + child` adds the key to the character to the current prefix, building the word one
				//  letter at a time with each recursion.
			}
		}

		// return the list of words
		return words;
	}


	/**
	 * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
	 * occurring word represented in the trie (according to the dictionary file)
	 * that begins with the provided prefix.
	 *
	 * @param prefix The prefix to search for
	 * @return The most frequent word that starts with prefix
	 */
	public String getMostFrequentWordWithPrefix(String prefix)
	{
		return prefix + "bogus";
	}

	/**
	 * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Reads in a dictionary from file
	 * and places all words into the trie.
	 *
	 * @param fileName the file to read from
	 * @return the trie containing all the words
	 */
	public static Trie readInDictionary(String fileName)
	{
		return null;
	}
}
