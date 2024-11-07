import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.commons.math3.genetics.UniformCrossover;

public class App {
	private static class PuzzleChromosome extends AbstractListChromosome<Integer> {
		private static final int MIN = 1;

		private static final int MAX = 10;

		private static final Random PRNG = new Random();

		public static PuzzleChromosome newRandomChromosome() {
			boolean done = false;
			int tiles[] = { 0, 0, 0, 0 };
			while (done == false) {
				done = true;
				for (int i = 0; i < tiles.length; i++) {
					tiles[i] = PRNG.nextInt(MAX - MIN + 1) + MIN;

					if (tiles[i] == 0) {
						done = false;
					}

					for (int j = 0; j < i; j++) {
						if (tiles[j] == tiles[i]) {
							done = false;
						}
					}
				}
			}

			ArrayList<Integer> representation = new ArrayList<>();

			for (int i = 0; i < tiles.length; i++) {
				representation.add(tiles[i]);
			}

			for (int i = 0; i < 24; i++) {
				switch (PRNG.nextInt(4)) {
				case 0:
					representation.add((int) '+');
					break;
				case 1:
					representation.add((int) '-');
					break;
				case 2:
					representation.add((int) '*');
					break;
				case 3:
					representation.add((int) '/');
					break;
				}
			}

			return new PuzzleChromosome(representation);
		}

		@Override
		protected void checkValidity(List<Integer> pepresentation) throws InvalidRepresentationException {
		}

		public PuzzleChromosome(List<Integer> representation) {
			super(representation, true);
		}

		@Override
		public PuzzleChromosome newFixedLengthChromosome(List<Integer> representation) {
			representation = new ArrayList<>(representation);
			return new PuzzleChromosome(representation);
		}

		private double calculate(int values[], char operations[]) {
			double result = values[0];

			for (int i = 0; i < operations.length; i++) {
				switch (operations[i]) {
				case '+':
					result += values[i + 1];
					break;
				case '-':
					result -= values[i + 1];
					break;
				case '*':
					result *= values[i + 1];
					break;
				case '/':
					result /= values[i + 1];
					break;
				}
			}

			return result;
		}

		@Override
		public double fitness() {
			int grid[][] = { { 0, 0, 1, 2, }, { 1, 1, 3, 0, }, { 3, 0, 3, 3, }, { 3, 1, 2, 1, }, };

			List<Integer> representation = getRepresentation();

			int tiles[] = { representation.get(0), representation.get(1), representation.get(2),
					representation.get(3), };

			double fitness = 0;

			char operators[] = {};
			int cells[] = {};

			cells = new int[] { tiles[grid[0][0]], tiles[grid[0][1]], tiles[grid[0][2]], tiles[grid[0][3]] };
			operators = new char[] { (char) representation.get(4).intValue(), (char) representation.get(5).intValue(),
					(char) representation.get(6).intValue(), };
			fitness += Math.pow(15 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[1][0]], tiles[grid[1][1]], tiles[grid[1][2]], tiles[grid[1][3]] };
			operators = new char[] { (char) representation.get(11).intValue(), (char) representation.get(12).intValue(),
					(char) representation.get(13).intValue(), };
			fitness += Math.pow(9 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[2][0]], tiles[grid[2][1]], tiles[grid[2][2]], tiles[grid[2][3]] };
			operators = new char[] { (char) representation.get(18).intValue(), (char) representation.get(19).intValue(),
					(char) representation.get(20).intValue(), };
			fitness += Math.pow(5 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[3][0]], tiles[grid[3][1]], tiles[grid[3][2]], tiles[grid[3][3]] };
			operators = new char[] { (char) representation.get(25).intValue(), (char) representation.get(26).intValue(),
					(char) representation.get(27).intValue(), };
			fitness += Math.pow(15 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[0][0]], tiles[grid[1][0]], tiles[grid[2][0]], tiles[grid[3][0]] };
			operators = new char[] { (char) representation.get(7).intValue(), (char) representation.get(14).intValue(),
					(char) representation.get(21).intValue(), };
			fitness += Math.pow(7 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[0][1]], tiles[grid[1][1]], tiles[grid[2][1]], tiles[grid[3][1]] };
			operators = new char[] { (char) representation.get(8).intValue(), (char) representation.get(15).intValue(),
					(char) representation.get(22).intValue(), };
			fitness += Math.pow(10 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[0][2]], tiles[grid[1][2]], tiles[grid[2][2]], tiles[grid[3][2]] };
			operators = new char[] { (char) representation.get(9).intValue(), (char) representation.get(16).intValue(),
					(char) representation.get(23).intValue(), };
			fitness += Math.pow(13 - calculate(cells, operators), 2);

			cells = new int[] { tiles[grid[0][3]], tiles[grid[1][3]], tiles[grid[2][3]], tiles[grid[3][3]] };
			operators = new char[] { (char) representation.get(10).intValue(), (char) representation.get(17).intValue(),
					(char) representation.get(24).intValue(), };
			fitness += Math.pow(14 - calculate(cells, operators), 2);

			return -fitness;
		}

		public List<Integer> representation() {
			return getRepresentation();
		}

		@Override
		public String toString() {
			int row[] = { 7, 10, 13, 14 };
			int column[] = { 15, 9, 5, 15 };
			int grid[][] = { { 0, 0, 1, 2, }, { 1, 1, 3, 0, }, { 3, 0, 3, 3, }, { 3, 1, 2, 1, }, };

			List<Integer> representation = getRepresentation();

			int tiles[] = { representation.get(0), representation.get(1), representation.get(2),
					representation.get(3), };

			String result = "";
			for (int i = 0, k = 4; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					result += tiles[grid[i][j]];
					result += "\t";

					if (j < grid[i].length - 1) {
						result += (char) representation.get(k++).intValue();
						result += "\t";
					}
				}
				result += "=";
				result += "\t";
				result += column[i];
				result = result.trim();
				result += "\n";
				result += "\n";

				for (int j = 0; j < grid[i].length && i < grid.length - 1; j++) {
					result += (char) representation.get(k++).intValue();
					result += "\t\t";
				}
				result = result.trim();
				result += "\n";
				result += "\n";
			}
			result = result.trim();
			result += "\n";
			result += "\n";

			for (int j = 0; j < row.length; j++) {
				result += "||";
				result += "\t\t";
			}
			result = result.trim();
			result += "\n";
			result += "\n";

			for (int j = 0; j < row.length; j++) {
				result += row[j];
				result += "\t\t";
			}
			result = result.trim();
			result += "\n";
			result += "\n";

			result += fitness();

			return result.trim();
		}
	}

	private static class PuzzleMutation implements MutationPolicy {
		private static final int MIN = -100;

		private static final int MAX = +100;

		private static Random PRNG = new Random();

		@Override
		public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
			List<Integer> representation = new ArrayList<>(((PuzzleChromosome) original).representation());

			int index = PRNG.nextInt(representation.size());
			if (index < 4) {
				boolean done = false;
				while (done == false) {
					done = true;
					representation.set(index, PRNG.nextInt(MAX - MIN + 1) + MIN);

					if (representation.get(index) == 0) {
						done = false;
					}

					for (int j = 0; j < 4; j++) {
						if (j == index) {
							continue;
						}

						if (representation.get(j) == representation.get(index)) {
							done = false;
						}
					}
				}
			} else {
				switch (PRNG.nextInt(4)) {
				case 0:
					representation.set(index, (int) '+');
					break;
				case 1:
					representation.set(index, (int) '-');
					break;
				case 2:
					representation.set(index, (int) '*');
					break;
				case 3:
					representation.set(index, (int) '/');
					break;
				}
			}

			return new PuzzleChromosome(representation);
		}
	}

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm(new UniformCrossover<Integer>(0.5), 0.9, new PuzzleMutation(), 0.01,
				new TournamentSelection(3));

		List<Chromosome> chromosomes = new ArrayList<Chromosome>();
		for (int i = 0; i < 37; i++) {
			chromosomes.add(PuzzleChromosome.newRandomChromosome());
		}

		Chromosome fittest = ga.evolve(new ElitisticListPopulation(chromosomes, chromosomes.size() * 2, 0.05),
				new FixedGenerationCount(100000)).getFittestChromosome();

		System.out.println(fittest.toString());
	}
}
