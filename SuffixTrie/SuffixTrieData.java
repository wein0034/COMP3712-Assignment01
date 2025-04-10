import java.util.ArrayList;

public class SuffixTrieData
{
	private ArrayList<SuffixIndex> startIndexes = null;

	public SuffixTrieData()
	{
		startIndexes = new ArrayList<>();
	}

	public ArrayList<SuffixIndex> getStartIndexes()
	{
		return startIndexes;
	}

	public void addStartIndex(SuffixIndex startIndex)
	{
		startIndexes.add(startIndex);
	}

	public String toString()
	{
		return startIndexes.toString();
	}
}
