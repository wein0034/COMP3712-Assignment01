import java.io.*;
import java.util.*;

public class Trie
{
	private final TrieNode root = new TrieNode();

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

			// ...
			if (currentNode.maxFrequency < data.getFrequency())
			{
				currentNode.maxFrequency = data.getFrequency();
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
		List<String> words = getWordsFromNode(getNode(prefix), prefix);

		//sort the list alphabetically
		Collections.sort(words);
		// because we are using Hashmaps to store child nodes, this can't be done during traversal

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
	private List<String> getWordsFromNode(TrieNode node, String prefix)
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
				words.addAll(getWordsFromNode(node.getChild(child), prefix + child));
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
		TrieNode node = getNode(prefix);

		// keep searching until the end of the chain, or the next node has a lower frequency than the current
		while ((node != null) && (node.getNumChildren() > 0))
		{
			// store which child has the highest frequency
			String childName = null;
			// this is a string for two reasons:
			//   1. it allows it to be null, for the purpose of checking if it has been modified later
			//   2. to make it easier to change if we allow strings as child names later

			// find the child with the highest frequency
			for(char child : node.getChildren().keySet())
			{
				// ignore children with lower frequency than the current node
				if (node.getChild(child).maxFrequency >= node.maxFrequency)
				{
					// store the child
					childName = String.valueOf(child);
				}
			}

			if (childName == null)
			{
				// if childName is still ' ' then there are no words further along the chain with a higher frequency
				// so return immediately
				return prefix;
			}
			else
			{
				// add the new suffix to the prefix
				prefix = prefix + childName;

				// set the current node to the child, then restart the loop
				node = node.getChild(childName.charAt(0));
			}
		}
		// theoretically, this implementation should find the most frequency word in O(n+1) time, where n is the length of the most frequent word

		return prefix;
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
		Trie trie = new Trie();
		Scanner fileScanner;

		try
		{
			// use a FileInputStream to ensure correct reading end-of-file
			//fileScanner = new Scanner(new FileInputStream("data" + File.separator + fileName));
			fileScanner = new Scanner(new FileInputStream(fileName));
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(" could not find the file \"" + fileName + "\" in the data directory!");
			return null;
		}

		int wordCount = 0;
		long startTime = System.nanoTime();

		while (fileScanner.hasNextLine())
		{
			String nextLine = fileScanner.nextLine();
			// System.out.println("nextLine: " + nextLine); uncomment if you want to see what is read in
			String[] splitLine = nextLine .split(" ");
			int rank = Integer.parseInt(splitLine[0]);
			String word = splitLine[1];
			int freq = Integer.parseInt(splitLine[2]);

			String other = "";
			// add whatever else to the "other" field
			for (int i = 3; i < splitLine.length; i++)
			{
				other = other + splitLine[i] + " ";
			}
			// not currently using it, but could later

			trie.insert(word, new TrieData(freq, rank));
			wordCount++;
		}

		System.out.println("Read in " + wordCount + " words in " + ((System.nanoTime() - startTime) / 1000000.0) + " ms.");
		return trie;
	}
}
