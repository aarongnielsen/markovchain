package org.aarongnielsen.markovchain.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.aarongnielsen.markovchain.model.MarkovChainModel;

/**
 * A quick demo that runs from the command line.
 * <p>
 * Usage: {@code java org.aarongnielsen.markovchain.cli input_file_name number_of_prefix_terms number_of_output_terms}
 */
public class Demo {

	private static void showUsageAndExit() {
		System.out.println("Usage: java " + Demo.class.getName() + " input_file_name number_of_prefix_terms number_of_output_terms");
		System.exit(1);
	}

	public static void main(String[] args) {
		// parse arguments
		if (args.length != 3) {
			showUsageAndExit();
		}
	
		File inputFile = new File(args[0]);
		if (!inputFile.canRead()) {
			System.out.println("cannot read input file " + inputFile.getAbsolutePath());
			showUsageAndExit();
		}

		int numberOfPrefixTerms = 0;
		try {
			numberOfPrefixTerms = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfx) {
			System.out.println("cannot parse prefix length parameter");
			showUsageAndExit();
		}

		int numberOfOutputTerms = 0;
		try {
			numberOfOutputTerms = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfx) {
			System.out.println("cannot parse output length parameter");
			showUsageAndExit();
		}

		MarkovChainModel model = new MarkovChainModel(numberOfPrefixTerms);
		try {
			String textForModel = Files.lines(Paths.get(inputFile.toURI()))
				.collect(Collectors.joining("\n"));
			model.addTextToModel(textForModel);
		} catch (IOException iox) {
			iox.printStackTrace();
		}

		String output = model.generateOutput(numberOfOutputTerms);
		System.out.println(output);
	}

}
