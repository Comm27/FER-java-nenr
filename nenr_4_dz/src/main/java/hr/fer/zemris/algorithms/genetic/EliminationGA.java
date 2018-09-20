package hr.fer.zemris.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;

public class EliminationGA<T> extends AbstractGeneticAlgorithm<T> {

	private ISolution<T> bestSolution;
	private boolean bestSolutionChanged;
	
	public EliminationGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		bestSolutionChanged = false;
		
		List<ISolution<T>> parents = new ArrayList<>();
		parents.add(getFirstParent(selectionOperator));
		parents.add(getNextParent(selectionOperator, parents.get(0)));
		parents.add(getNextParent(selectionOperator, parents.get(0), parents.get(1)));
		parents.sort((s1, s2) -> Double.compare(s2.getFitness(), s1.getFitness()));
		
		population.remove(parents.get(parents.size() - 1));
		ISolution<T> child = getChild(crossoverOperator, mutationOperator, parents.get(0), parents.get(1));
		population.add(child);
		
		if (bestSolution == null ||
				bestSolution.getFitness() < population.getBestSolution().getFitness()) {
			bestSolution = population.getBestSolution();
			bestSolutionChanged = true;
		}
		
		return population;
	}

	@Override
	boolean printSolution(ISolution<T> solution) {
		return bestSolutionChanged;
	}
}
