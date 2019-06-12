package org.aarongnielsen.markovchain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a list of suffixes mapped to a given prefix in a Markov chain model.
 * <p>
 * It also provides functionality to choose a suffix from the list, given that prefix.
 * If a suffix has occurred frequently in the model (i.e. if a suffix has high multiplicity),
 * it has a higher probability of being selected.
 */
public class MarkovChainSuffixes {

	private List<MarkovChainSuffix> suffixes;

	public MarkovChainSuffixes() {
		this.suffixes = new ArrayList<>();
	}

	public List<MarkovChainSuffix> getSuffixes() {
		return new ArrayList<MarkovChainSuffix>(this.suffixes);
	}

	public void addSuffix(MarkovChainSuffix newSuffix) {
		int existingSuffixIndex = suffixes.indexOf(newSuffix);
		if (existingSuffixIndex == -1) {
			this.suffixes.add(newSuffix);
		} else {
			this.suffixes.get(existingSuffixIndex).incrementMultiplicity();
		}
	}

	private int getTotalMultiplicity() {
		return this.suffixes.stream()
				.mapToInt(MarkovChainSuffix::getMultiplicity)
				.sum();
	}

	public MarkovChainSuffix chooseRandomSuffix() {
		// This algorithm uses the suffixes' multiplicity as a weight for a random number generator:
		//  - pick a random number in the range 0 ... getTotalMultiplicity()
		//  - step through the list of suffixes while decrementing the random number
		//    by the multiplicity of each suffix
		//  - once the number is exhausted, the current suffix is returned
		int randomValue = new Random().nextInt(this.getTotalMultiplicity());
		for (MarkovChainSuffix suffix : this.suffixes) {
			randomValue -= suffix.getMultiplicity();
			if (randomValue < 0) {
				return suffix;
			}
		}
		// should never happen
		return null;
	}

	@Override
	public String toString() {
		return this.suffixes.toString();
	}
}
