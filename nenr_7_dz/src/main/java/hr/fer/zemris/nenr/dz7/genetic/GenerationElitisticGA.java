package hr.fer.zemris.nenr.dz7.genetic;

import java.util.ArrayList;
import java.util.List;

public class GenerationElitisticGA<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 2;
	
	public GenerationElitisticGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T>[] crossoverOperators,
			IMutationOperator<T>[] mutationOperators,
			double mutationChooser,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperators,
			mutationOperators,
			mutationChooser,
			problem,
			fitnessThreshold,
			maxGenerations
		);
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<ISolution<T>> solutions = new ArrayList<>();
		solutions.addAll(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		
		IPopulation<T> newPopulation = new Population<>(solutions);
		
		while (newPopulation.getSize() < population.getSize()) {
			ISolution<T> parent1 = getFirstParent(selectionOperator);
			@SuppressWarnings("unchecked")
			ISolution<T> parent2 = getNextParent(selectionOperator, parent1);
			ISolution<T> child = getChild(crossoverOperators, mutationOperators, parent1, parent2);
			
			newPopulation.add(child);
		}
		
		return newPopulation;
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}
