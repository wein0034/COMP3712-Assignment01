public class SuffixTrie
{

	private SuffixTrieNode root = new SuffixTrieNode();

	/**
	 * Insert a String into the suffix trie.  For the assignment the string str
	 * is a sentence from the given text file.
	 *
	 * @param str           the sentence to insert
	 * @param startPosition the starting index/position of the sentence
	 * @return the final node inserted
	 */
	public SuffixTrieNode insert(String str, int startPosition)
	{
		return null;
	}

	/**
	 * Get the suffix trie node associated with the given (sub)string.
	 *
	 * @param str the (sub)string to search for
	 * @return the final node in the (sub)string
	 */
	public SuffixTrieNode get(String str)
	{
		return null;
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
		return new SuffixTrie();
	}
}