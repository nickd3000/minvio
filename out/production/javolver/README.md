# Javolver
**Javolver** is a simple yet powerful **genetic algorithm** system in Java.

![Evolved Tree](https://i.imgur.com/YQOhyQV.png "Evolved tree")

The library is designed to be extremely simple to set up and run.  To get strted, create a derived class from the [Individual](https://github.com/nickd3000/javolver/blob/master/javolver/Individual.java) class, and implement three functions:
    clone()
    toString();
    calculateScore();
    
An object of Javolver is then created and passed in the user's derived type, and then the fun can begin.

*Documentation*
Java docs for the library are available [here](http://htmlpreview.github.com/?https://github.com/nickd3000/javolver/blob/master/docs/index.html)

Simple Example

``` 
    /**
	 * Example testing the CWord class.
	 * Individuals of type CWord are scored by how well they spell
	 * out a string compared to the supplied target word.
	 */
	public static void testWord() {

		int populationSize = 100;
		String targetWord = "HELLOWORLD";
		Javolver testEvolver = new Javolver(new CWord(targetWord), populationSize);
		
		// Configure the engine (Not required).
		testEvolver.setKeepBestIndividualAlive(false);
		testEvolver.setMutationCount(1);
		testEvolver.setMutationAmount(1.0/20.0);
		testEvolver.setSelectionType(SELECTION_TYPE.tournament);
		testEvolver.setSelectionRange(0.25);
		testEvolver.setDiversityAmount(1.0);
		
		// Perform a few iterations of evolution.
		for (int j = 0; j < 30; j++) {
			
			// Call the evolver class to perform one evolution step.
			testEvolver.doOneCycle();
			
			// Print output every so often.
			System.out.println("Iteration " + j + "  " + testEvolver.report());
		}

		System.out.print("END ");
	}
```

There are a few tests included in the project, a simulation that [evolves trees](https://github.com/nickd3000/javolver/blob/master/test/GeneTree.java), one that [packs arbitrarily sized circles](https://github.com/nickd3000/javolver/blob/master/test/CSpherePacker.java) into a set area and a simple [word finding algorithm](https://github.com/nickd3000/javolver/blob/master/test/CWord.java).

![Sphere Packing](https://i.imgur.com/sidizaf.png "Sphere Packing")
![Basic test](https://i.imgur.com/TT5nKZB.png "Basic test")
![Image Solver](http://i.imgur.com/455ZMAx.png "Image solving")

