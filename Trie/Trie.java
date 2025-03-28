import java.util.*;

public class Trie {

    private TrieNode root = new TrieNode();

    /**
     * Inserts a string into the trie and returns the last node that was
     * inserted.
     *
     * @param str The string to insert into the trie
     * @param data	The data associated with the string
     * @return The last node that was inserted into the trie
     */
    public TrieNode insert(String str, TrieData data) {
        // hint you can use str.toCharArray() to get the char[] of characters

        return null;
    }

    /**
     * Search for a particular prefix in the trie, and return the final node in
     * the path from root to the end of the string, i.e. the node corresponding
     * to the final character. getNode() differs from get() in that getNode()
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
    public TrieNode getNode(String str) {
        // hint you can use str.toCharArray() to get the char[] of characters

        return null;
    }

    /**
     * Searches for a word in the trie, and returns the final node in the search
     * sequence from the root, i.e. the node corresponding to the final
     * character in the word.
     *
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
    public TrieNode get(String str) {
        // hint you can use str.toCharArray() to get the char[] of characters

        // hint use getNode to find the end node and then check to see if it is
        // not null and a terminal

        return null;
    }

    /**
     * Retrieve from the trie an alphabetically sorted list of all words
     * beginning with a particular prefix.
     *
     * @param prefix The prefix with which all words start.
     * @return The list of words beginning with the prefix, or an empty list if
     * the prefix was not found.
     */
    public List<String> getAlphabeticalListWithPrefix(String prefix) {
        return null;
    }

    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
     * occurring word represented in the trie (according to the dictionary file)
     * that begins with the provided prefix.
     *
     * @param prefix The prefix to search for
     * @return The most frequent word that starts with prefix
     */
    public String getMostFrequentWordWithPrefix(String prefix) {
        return prefix + "bogus";
    }

    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Reads in a dictionary from file
     * and places all words into the trie.
     *
     * @param fileName the file to read from
     * @return the trie containing all the words
     */
    public static Trie readInDictionary(String fileName) {
        return new Trie();
    }
}
