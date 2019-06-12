package org.aarongnielsen.markovchain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This class represents a text model, built from an input text file,
 * generated using a Markov chain algorithm.
 */
public class MarkovChainModel {

	static final String NONWORD = "#NonwordPrefix#";

	private Map<MarkovChainPrefix, MarkovChainSuffixes> currentModel;
	private int prefixLength;

	private MarkovChainPrefix lastPrefix;

	public MarkovChainModel(int prefixLength) {
		this.prefixLength = prefixLength;

		// create a dummy prefix to begin with
		this.lastPrefix = this.createBlankPrefix();

		// start with a blank model
		this.currentModel = new HashMap<>();
	}

	public int getPrefixLength() {
		return this.prefixLength;
	}

	
	// methods - building the model

	private MarkovChainPrefix createBlankPrefix() {
		List<String> nonwordPrefixStrings = new ArrayList<String>(this.prefixLength);
		for (int i = 0; i < this.prefixLength; i++) {
			nonwordPrefixStrings.add(MarkovChainModel.NONWORD);
		}
		return new MarkovChainPrefix(nonwordPrefixStrings);
	}
	
	public void addTextToModel(String text) {
		// once we run out of text, we stil want to add a nonword suffix for the last prefix
		StringTokenizer strtok = new StringTokenizer(text + " " + MarkovChainModel.NONWORD);

		while (strtok.hasMoreTokens()) {
			String term = strtok.nextToken();

			// add a new suffix, given the current term
			MarkovChainSuffixes suffixes = this.currentModel.get(lastPrefix);
			if (suffixes == null) {
				suffixes = new MarkovChainSuffixes();
				this.currentModel.put(lastPrefix, suffixes);
			}
			suffixes.addSuffix(new MarkovChainSuffix(term));

			// shuffle the prefix terms across by one
			lastPrefix = lastPrefix.createNextPrefix(term);
		}
	}


	// methods - building output from the model

	public String generateOutput(int numberOfTerms) {
		StringBuilder sb = new StringBuilder();
		MarkovChainPrefix currentPrefix = this.createBlankPrefix();
		for (int i = 0; i < numberOfTerms; i++) {
			MarkovChainSuffixes currentSuffixes = this.currentModel.get(currentPrefix);
			if (currentSuffixes != null) {
				MarkovChainSuffix nextSuffix = currentSuffixes.chooseRandomSuffix();
				sb.append(nextSuffix.getSuffixString()).append(" ");
				currentPrefix = currentPrefix.createNextPrefix(nextSuffix.getSuffixString());
			} else {
				currentPrefix = currentPrefix.createNextPrefix(MarkovChainModel.NONWORD);
			}
		}
		return sb.toString();
	}

}
