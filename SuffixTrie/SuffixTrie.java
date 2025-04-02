import java.io.*;
import java.util.Scanner;

public class SuffixTrie
{

	private SuffixTrieNode root = new SuffixTrieNode(-1,-1);

	/**
	 * Insert a String into the suffix trie.  For the assignment the string str
	 * is a sentence from the given text file.
	 *
	 * @param str           the sentence to insert.
	 * @param startPosition the starting index/position of the sentence.
	 */
	public void insert(String str, int startPosition)
	{
		// ensure pattern is case insensitive
		str = str.toLowerCase();

		for (int startFrom = 0; startFrom < str.length(); startFrom++)
		{
			String substr = str.substring(startFrom);

			char[] chars = substr.toCharArray();
			SuffixTrieNode currentNode = root;

			boolean add = false;
			for (int i = 0;i < chars.length; i++)
			{
				if (!add)
				{
					if (currentNode.getChild(chars[i]) != null)
					{
						// search through the tree as long as the current string already exists
						currentNode = currentNode.getChild(chars[i]);

						// add the data for the new string to the current node
						currentNode.addData(startPosition, startFrom + i);

						// restart the loop early to avoid adding anything below
						continue;
					}
					else
					{
						// when a missing char is found, switch add to true so it skips searching,
						// then move on to add the new char below
						add = true;
					}
				}

				// add the current char, including its data
				currentNode.addChild(chars[i], startPosition, startFrom + i);

				// move to node that matches the char we want to add
				currentNode = currentNode.getChild(chars[i]);

				//continue adding the rest of the string
			}

		}

		// set the current (last added/accessed) node to be terminal
		//currentNode.setTerminal(true);

		return;
	}

	/**
	 * Get the suffix trie node associated with the given (sub)string.
	 *
	 * @param str the (sub)string to search for
	 * @return the final node in the (sub)string
	 */
	public SuffixTrieNode get(String str)
	{
		// ensure pattern is case insensitive
		str = str.toLowerCase();

		char[] chars = str.toCharArray();
		SuffixTrieNode currentNode = root;

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
	 * Helper/Factory method to build a SuffixTrie object from the text in the
	 * given file.  The text file is broken up into sentences and each sentence
	 * is inserted into the suffix trie.
	 * <p>
	 * It is called in the following way
	 * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
	 *
	 * @param fileName
	 * @return
	 */
	public static SuffixTrie readInFromFile(String fileName)
	{
		SuffixTrie trie = new SuffixTrie();

		Scanner scanner = null;
		try
		{
			// use a FileInputStream to ensure correct reading end-of-file
			scanner = new Scanner(new FileInputStream("SuffixTrie" + File.separator + "data" + File.separator + fileName));
			//scanner = new Scanner(new BufferedReader(new FileReader("SuffixTrie/data/" + fileName)));
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(" could not find the file \"" + ("SuffixTrie/data/" + fileName) + "\" in the data directory!");
			return null;
		}

		int sentanceCounter = 0;
		int wordCounter = 0;

		long startTime = System.nanoTime();
		while (scanner.hasNext())
		{
			StringBuilder sentence = new StringBuilder();

			// Load the next sentence (read until the next '.')
			while (scanner.hasNext())
			{
				sentence.append(scanner.next());    // Scan until the next whitespace
				sentence.append(" ");   // Add the whitespace back in that is removed by next()
				// Once a full stop is found as the last character, the sentence is complete
				char end = sentence.charAt(sentence.length() - 2);
				if ((end == '.') || (end == '!') || (end == '?'))
				{
					// TODO fix sentence detection. perhaps using "BreakIterator.getSentenceInstance(Locale.US)"?
					break;
				}
			}
			//System.out.println("Reading: " + sentence); // uncomment if you want to see what is read in

			trie.insert(String.valueOf(sentence), sentanceCounter);

//			// Split all the words in the sentence into an array (deliminate with ' ')
//			String[] words = sentence.toString().split(" ");
//
//			// Add each word to the trie
//			for (String word : words)
//			{
//				trie.insert(word, sentanceCounter);
//				wordCounter++;
//			}
			sentanceCounter++;
		}

		System.out.println("Read in " + sentanceCounter + " sentences containing " + wordCounter + " words in " + ((System.nanoTime() - startTime) / 1000000.0) + " ms.");
		return trie;
	}
}
