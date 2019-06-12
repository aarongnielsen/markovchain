# markovchain
A brief demonstration of a Markov chain algorithm.

Simply run Ant to build from source and run a demonstration.

## Explanation

The command-line application takes three input parameters:
- the name of an input file;
- the length of each prefix, in words, to use when building the model;
- the number of words of output to display, once the model is built.

The output is a string of text built using the generated model. This is written to standard output.

There really isn't much to it, other than that. I didn't find an example of a Markov chain that used suffixes longer than a single word, but I don't think it would be difficult (other than in making the time for it) to extend the code that I've got to allow for it.

AGN
