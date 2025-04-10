import java.io.*;
import java.text.BreakIterator;
import java.util.*;

public class SuffixTrie
{

	private final SuffixTrieNode root = new SuffixTrieNode(-1, -1);

	/**
	 * Insert a String into the suffix trie.  For the assignment the string str
	 * is a sentence from the given text file.
	 *
	 * @param input       The sentence to insert.
	 * @param sentencePos The starting index/position of the sentence.
	 */
	public void insert(String input, int sentencePos)
	{
		// ensure pattern is case-insensitive and has no leading or trailing whitespace
		input = CleanString(input);

		// repeat the insert process n times, where n is the length of the input, starting from one position further along each time
		for (int startFrom = 0; startFrom < input.length(); startFrom++)
		{
			String inputRemaining = input.substring(startFrom);
			SuffixTrieChild currentNode = new SuffixTrieChild("", root);

			// check if there is a node to use
			SuffixTrieChild newNode = currentNode.Value().getChild(inputRemaining);

			// if there isn't, add the rest of the string as a single node, including its data
			if (newNode == null)
			{
				currentNode.Value().addChild(inputRemaining, sentencePos, startFrom);
			}
			// if there is, find how much of it matches
			else
			{
				int nodePos = 0;
				SuffixTrieNode previousNode = currentNode.Value();
				for (int addPos = 0; addPos < inputRemaining.length(); addPos++)
				{
					// if the current node has been full searched, try and find a continuation in a child node
					if (nodePos == currentNode.Label().length())
					{
						// add the data for the new string to the current node
						currentNode.Value().addData(sentencePos, startFrom);

						// update the previous node before moving to the next node
						previousNode = currentNode.Value();

						// look for a child node starting with the next char in the pattern
						currentNode = currentNode.Value().getChild(inputRemaining.substring(addPos));

						// if no child was found, add the rest of the input as a child of the previous node
						if (currentNode == null)
						{
							previousNode.addChild(inputRemaining.substring(addPos), sentencePos, startFrom + addPos);
							break;
						}

						// reset our position in the node back to the start for the new node
						nodePos = 0;
					}
					// if there is a mismatch, then split the current node in half,
					// then add the rest of the input and second half as a child of the first half
					else if (inputRemaining.charAt(addPos) != currentNode.Label().charAt(nodePos))
					{
						// split the label of the current node in two
						String firstHalfLabel = currentNode.Label().substring(0, nodePos);
						String secondHalfLabel = currentNode.Label().substring(nodePos);

						// create a new node to act as the first part
						SuffixTrieNode firstHalfNode = new SuffixTrieNode(currentNode.Value().data);

						// add the data for the new string to the first part
						firstHalfNode.addData(sentencePos, startFrom);

						// add the current node as a child of the first part
						firstHalfNode.replaceNode("", secondHalfLabel, currentNode.Value());

						// create a new node for the remainder of the input and add it as a child of the first part
						firstHalfNode.addChild(inputRemaining.substring(addPos), sentencePos, startFrom);

						// remove the current node from the previous node, and add the first part as a new child
						previousNode.replaceNode(currentNode.Label(), firstHalfLabel, firstHalfNode);

						break;
					}

					// advance forward one position in the current node
					// this being at the end means that new nodes will always start at position 1, since we know 0 has to match since that's how we found the node
					nodePos++;
				}
			}
		}
	}

	/**
	 * Get the suffix trie node associated with the given (sub)string.
	 *
	 * @param pattern the (sub)string to search for.
	 * @return a SuffixTrieChild object containing the final node in the (sub)string, and the label of that node.
	 */
	public SuffixTrieChild getChild(String pattern)
	{
		// ensure pattern is case-insensitive and has no leading or trailing whitespace
		pattern = CleanString(pattern);

		SuffixTrieChild currentNode = new SuffixTrieChild("", root);

		// store the position within the current node to compare to
		int nodePos = 0;

		// search through the tree following the pattern
		for (int patternPos = 0; patternPos < pattern.length(); patternPos++)
		{
			// if the current node has been full searched, try and find a continuation in a child node
			if (nodePos == currentNode.Label().length())
			{
				// look for a child node starting with the next char in the pattern
				currentNode = currentNode.Value().getChild(pattern.substring(patternPos));

				// if no child was found, the string is not present
				if (currentNode == null)
				{
					return null;
				}

				// reset our position in the node back to the start for the new node
				nodePos = 0;
			}
			// if there is a mismatch, then the string is not present
			else if (pattern.charAt(patternPos) != currentNode.Label().charAt(nodePos))
			{
				return null;
			}

			// advance forward one position in the current node
			// this being at the end means that new nodes will always start at position 1, since we know 0 has to match since that's how we found the node
			nodePos++;
		}

		// if the whole pattern has been found, return the current node we're looking at
		return currentNode;
	}

	/**
	 * Get the suffix trie node associated with the given (sub)string.
	 *
	 * @param pattern the (sub)string to search for.
	 * @return the final node in the (sub)string.
	 */
	public SuffixTrieNode get(String pattern)
	{
		return getChild(pattern).Value();
	}

	/**
	 * Helper/Factory method to build a SuffixTrie object from the text in the
	 * given file.  The text file is broken up into sentences and each sentence
	 * is inserted into the suffix trie.
	 * <p>
	 * It is called in the following way
	 * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
	 *
	 * @param fileName The name of the file within the 'SuffixTrie/data/' folder to import.
	 */
	public static SuffixTrie readInFromFile(String fileName)
	{
		SuffixTrie trie = new SuffixTrie();

		Scanner scanner;
		try
		{
			// use a FileInputStream to ensure correct reading end-of-file
			scanner = new Scanner(new FileInputStream("SuffixTrie" + File.separator + "data" + File.separator + fileName));
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(" could not find the file \"" + ("SuffixTrie/data/" + fileName) + "\" in the data directory!");
			return null;
		}

		int sentenceCounter = 0;
		int wordCounter = 0;

		long startTime = System.nanoTime();

		// create a blank string which will be used to "scroll" through the file
		String currentText = "";

		// define where to end sentences using BreakIterator
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

		// begin scanning, keep going until the file has been fully read
		while (scanner.hasNext())
		{
			// grab the next line from the file and add it to the current text
			currentText += scanner.nextLine() + " ";
			iterator.setText(currentText);

			// read all the sentences from the current text into a list
			List<String> sentences = new ArrayList<>();
			int start = iterator.first();
			for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next())
			{
				sentences.add(currentText.substring(start, end));
			}

			// add all the sentences read so far into the trie
			// the last sentence in the list is ONLY added to the tree if there is nothing else to read from the file
			for (int sentencePos = 0; sentencePos < sentences.size() - (scanner.hasNext() ? 1 : 0); sentencePos++)
			{
				// insert the extracted sentence into the trie
				trie.insert(sentences.get(sentencePos), sentenceCounter);
				sentenceCounter++;

				// remove the extracted sentence from the read text
				currentText = currentText.substring(sentences.get(sentencePos).length());
			}
		}

		System.out.println("Read in " + sentenceCounter + " sentences containing " + wordCounter + " words in " + ((System.nanoTime() - startTime) / 1000000.0) + " ms.");
		return trie;
	}

	/**
	 * Helper method to remove whitespace and convert string to lowercase.
	 *
	 * @param str The string to clean.
	 * @return The input string converted to lowercase with all leading and trailing whitespace removed.
	 */
	private String CleanString(String str)
	{
		// to prevent @NotNull methods from causing problems, return null strings immediately.
		if (str == null)
		{
			return null;
		}

		str = str.trim();
		str = str.toLowerCase();
		return str;
	}
}
