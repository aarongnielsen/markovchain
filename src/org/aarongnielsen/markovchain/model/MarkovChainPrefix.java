package org.aarongnielsen.markovchain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the current prefix stored in a Markov chain model.
 * The number of prefix terms is constant, but it is not stored here;
 * instead, it is passed in when constructed by a {@link MarkovChainModel}
 * and preserved if the prefix is progressed via the {@link #createNextPrefix(String)} method.
 * <p>
 * Note that objects of this type are immutable.
 */
public class MarkovChainPrefix {

	private List<String> prefixStrings = new ArrayList<>();

	public MarkovChainPrefix(List<String> prefixStrings) {
		this.prefixStrings.addAll(prefixStrings);
	}

	public List<String> getPrefixStrings() {
		return new ArrayList<String>(this.prefixStrings);
	}

	public MarkovChainPrefix createNextPrefix(String nextTerm) {
		List<String> newPrefixes = new ArrayList<String>(this.prefixStrings);
		newPrefixes.remove(0);
		newPrefixes.add(nextTerm);
		return new MarkovChainPrefix(newPrefixes);
	}

	@Override
	public String toString() {
		return this.prefixStrings.stream()
				.collect(Collectors.joining(" "));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MarkovChainPrefix)) {
			return false;
		}
		return this.prefixStrings.equals(((MarkovChainPrefix) obj).prefixStrings);
	}

	@Override
	public int hashCode() {
		return this.prefixStrings.hashCode();
	}
}
