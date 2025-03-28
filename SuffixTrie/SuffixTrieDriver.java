/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;

/**
 * This is an example class to driver the Suffix Trie project.  You can use this a starting point
 * to test your Suffix Trie implementation.
 *
 * It expects user input of the file to processes as the first line and then the subsequent lines are
 * the words/phrases/suffixes to search for with an empty line terminating the user input. For example:
 *
 * java cp3.ass01.suffixtrie.SuffixTrieDriver
 * data/Frank02.txt
 * and
 * the
 * , the
 * onster
 * ngeuhhh
 * ing? This
 *
 * @author lewi0146
 */
public class SuffixTrieDriver {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        Queue<String> ss = new ArrayDeque<>();
        String suffix = in.nextLine();

        while (!suffix.equals(""))
        {
            ss.offer(suffix);
            suffix = in.nextLine();
        }

        SuffixTrie st = SuffixTrie.readInFromFile(fileName);

        while (!ss.isEmpty()) {
            String s = ss.poll();
            SuffixTrieNode sn = st.get(s);
            System.out.println("[" + s + "]: " + sn);
        }
    }
}
