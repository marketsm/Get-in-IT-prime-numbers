package primes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/*
 * Der Algorithmus ist von https://de.wikipedia.org/wiki/Sieb_von_Atkin.
 * 
 * Lösung Implimeniter von Christoph Marketsmüller Geb. 01.04.1993 am 19.02.2017 
 */
public class SieveOfAtkin {

	public static void main(String[] args) {
		SieveOfAtkin.getPrimes(100000);

	}

	public static void getPrimes(int limit) {

		Map<Integer, Boolean> sieve = new HashMap<Integer, Boolean>();
		List<Integer> primes = new LinkedList<Integer>();

		if (limit < 2) {
		} else if (limit < 3) {
			primes.add(2);
		} else if (limit < 5) {
			primes.add(2);
			primes.add(3);
		} else if (limit < 6) {
			addStartValuestoList(primes);
		} else {

			// Main falls limit > 5
			sieve = createSieveMap(limit);
			inversion(sieve);
			primes = markMulipleOfPrimes(sieve, primes, limit);

		}

		// System.out.println("Primzahlen = " + primes);
		System.out.println("Anzahl = " + primes.size());

	}

	/*
	 * Siehe Wikipedia Algorithmus Schritt 1
	 */
	private static void addStartValuestoList(List<Integer> primes) {
		primes.add(2);
		primes.add(3);
		primes.add(5);
	}

	/*
	 * Siehe Wikipedia Algorithmus Schritt 2
	 */
	private static Map<Integer, Boolean> createSieveMap(int limit) {
		Map<Integer, Boolean> sieve = new HashMap<Integer, Boolean>();
		for (int i = 0; i < limit; i++) {
			sieve.put(i + 1, false);
		}
		return sieve;
	}

	/*
	 * Siehe Wikipedia Algorithmus Schritt 3
	 */
	private static void inversion(Map<Integer, Boolean> sieve) {
		int[] modFourRestOne = { 1, 13, 17, 29, 37, 41, 49, 53 };
		int[] modSixRestOne = { 7, 19, 31, 43 };
		int[] modTwelveRestOne = { 11, 23, 47, 59 };

		for (int i = 2; i <= sieve.size(); i++) {
			int modSixtyRest = i % 60;
			int countOfPossibleSolutions = 0;
			if (IntStream.of(modFourRestOne).anyMatch(x -> x == modSixtyRest)) {
				countOfPossibleSolutions = getPossibleSolutionsByModFour(i);

			} else if (IntStream.of(modSixRestOne).anyMatch(x -> x == modSixtyRest)) {
				countOfPossibleSolutions = getPossibleSolutionsByModSix(i);
			} else if (IntStream.of(modTwelveRestOne).anyMatch(x -> x == modSixtyRest)) {
				countOfPossibleSolutions = getPossibleSolutionsByModTwelve(i);
			}

			if (countOfPossibleSolutions % 2 == 1) {
				sieve.put(i, true);
			}

		}
	}

	/*
	 * Siehe Wikipedia Algorithmus Schritt 4 -8
	 */
	private static List<Integer> markMulipleOfPrimes(Map<Integer, Boolean> sieve, List<Integer> primes,int limit) {
		addStartValuestoList(primes);
		for (int i = 1; i <= sieve.size(); i++) {
			if (sieve.get(i)) {
				primes.add(i);
				int square = (int) Math.pow(i, 2);
				for (int j = square; j <= limit; j += square) {
					sieve.put(j, false);
				}
			}
		}
		return primes;
	}

	/*
	 * 
	 * Siehe Wikipedia Algorithmus Schritt 3 Unterpunkte 1
	 * 
	 */
	private static int getPossibleSolutionsByModFour(int result) {
		double breakLoop = Math.sqrt(result);
		int countOfSolutions = 0;
		for (int x = 0; x <= breakLoop; x++) {
			for (int y = 0; y <= breakLoop; y++) {
				double calculation = 4 * Math.pow(x, 2) + Math.pow(y, 2);
				if (calculation == result) {
					countOfSolutions++;
				}
			}
		}
		return countOfSolutions;
	}

	/*
	 * 
	 * Siehe Wikipedia Algorithmus Schritt 3 Unterpunkte 2
	 * 
	 */
	private static int getPossibleSolutionsByModSix(int result) {
		double breakLoop = Math.sqrt(result);
		int countOfSolutions = 0;
		for (int x = 0; x <= breakLoop; x++) {
			for (int y = 0; y <= breakLoop; y++) {
				double calculation = 3 * Math.pow(x, 2) + Math.pow(y, 2);
				if (calculation == result) {
					countOfSolutions++;
				}
			}
		}
		return countOfSolutions;
	}

	/*
	 * 
	 * Siehe Wikipedia Algorithmus Schritt 3 Unterpunkte 3
	 * 
	 */
	private static int getPossibleSolutionsByModTwelve(int result) {
		double calculation = 0;
		int countOfSolutions = 0;
		for (int x = 2; calculation <= result; x++) {
			for (int y = 1; y < x; y++) {
				calculation = 3 * Math.pow(x, 2) - Math.pow(y, 2);
				if (calculation == result) {
					countOfSolutions++;
				}
			}
		}
		return countOfSolutions;
	}
}
