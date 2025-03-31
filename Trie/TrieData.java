public class TrieData
{

	private int frequency = -1;
	private int rank = -1;

	/**
	 * Construct a new TrieData with the given frequency
	 *
	 * @param frequency the frequency of the data associated with the TrieNode
	 */
	public TrieData(int frequency)
	{
		this.frequency = frequency;
	}
	/**
	 * Construct a new TrieData with the given frequency
	 *
	 * @param frequency the frequency of the data associated with the TrieNode
	 */
	public TrieData(int frequency, int rank)
	{
		this.frequency = frequency;
		this.rank = rank;
	}

	/**
	 * Gets the frequency of this TrieData.
	 *
	 * @return the frequency
	 */
	public int getFrequency()
	{
		return frequency;
	}

	/**
	 * Gets the rank of this TrieData.
	 *
	 * @return the rank
	 */
	public int getRank()
	{
		return rank;
	}

	@Override
	public String toString()
	{
		return String.valueOf(frequency);
	}
}
