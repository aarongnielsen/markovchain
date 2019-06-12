package org.aarongnielsen.markovchain.model;

/**
 * This class repesents a suffix in a Markov chain model.
 * It stores the suffix as a term, but it keeps a count to indicate
 * how many times it was used in the construction of the model,
 * and thereby how likely it is to appear in text generated from that model.
 */
public class MarkovChainSuffix {

	private String suffixString;
	private int multiplicity;

	public MarkovChainSuffix(String suffix) {
		this.suffixString = suffix;
		this.multiplicity = 1;  // assume we're creating it because we've seen it
	}

	public String getSuffixString() {
		return suffixString;
	}
	
	public int getMultiplicity() {
		return multiplicity;
	}

	public void incrementMultiplicity() {
		multiplicity++;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", this.suffixString, this.multiplicity);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MarkovChainSuffix)) {
			return false;
		}
		return this.suffixString.equals(((MarkovChainSuffix) obj).suffixString);
	}

	@Override
	public int hashCode() {
		return this.suffixString.hashCode();
	}

}
