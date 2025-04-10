public class SuffixTrieChild
{
	// This object exists for cases where a child needs to be passed around alongside its label.
	// This method allows the child to not have to store its label as a variable inside itself, cutting down its size.

	public SuffixTrieChild(String label, SuffixTrieNode child)
	{
		this.label = label;
		this.value = child;
	}

	private String label = "";
	private SuffixTrieNode value = null;

	public SuffixTrieNode Value()
	{
		return value;
	}

	public String Label()
	{
		return label;
	}
}