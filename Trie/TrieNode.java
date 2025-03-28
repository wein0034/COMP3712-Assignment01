public class TrieNode {

    private TrieData data = null;
    private boolean terminal = false;
    private int numChildren = 0;

    /**
     * Lookup a child node of the current node that is associated with a
     * particular character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public TrieNode getChild(char label) {
        return null;
    }

    /**
     * Add a child node to the current node, and associate it with the provided
     * label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, TrieNode node) {
    }

    /**
     * Add a new data object to the node.
     *
     * @param dataObject The data object to be added to the node.
     */
    public void addData(TrieData dataObject) {
    }

    /**
     * The toString() method for the TrieNode that outputs in the format
     *   TrieNode; isTerminal=[true|false], data={toString of data}, #children={number of children}
     * for example,
     *   TrieNode; isTerminal=true, data=3, #children=1
     * @return
     */
    @Override
    public String toString() {
        return "Not implemented yet";
    }
}

