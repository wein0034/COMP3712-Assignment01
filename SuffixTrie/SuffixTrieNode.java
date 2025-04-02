public class SuffixTrieNode
{

	SuffixTrieData data = null;
	int numChildren = 0;

	public SuffixTrieNode getChild(char label)
	{
		return null;
	}

	public void addChild(char label, SuffixTrieNode node)
	{
	}

	public void addData(int sentencePos, int characterPos)
	{
	}

	public String toString()
	{
		return data.toString();
	}
}
