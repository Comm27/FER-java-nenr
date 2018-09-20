package hr.fer.zemris.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;

public class GenerationElitisticGA<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 2;
	private boolean elitistic;
	
	private ISolution<T> bestSolution;
	private boolean bestSolutionChanged;
	
	public GenerationElitisticGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			boolean elitistic) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
		
		this.elitistic = elitistic;
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		bestSolutionChanged = false;
		List<ISolution<T>> solutions = new ArrayList<>();
		
		if (elitistic) {
			solutions.addAll(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		}
		
		IPopulation<T> newPopulation = new Population<>(solutions);
		
		while (newPopulation.getSize() < population.getSize()) {
			ISolution<T> parent1 = getFirstParent(selectionOperator);
			ISolution<T> parent2 = getNextParent(selectionOperator, parent1);
			ISolution<T> child = getChild(crossoverOperator, mutationOperator, parent1, parent2);
			
			newPopulation.add(child);
		}
		
		if (bestSolution == null ||
				bestSolution.getFitness() < newPopulation.getBestSolution().getFitness()) {
			bestSolution = newPopulation.getBestSolution();
			bestSolutionChanged = true;
		}
		
		return newPopulation;
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return bestSolutionChanged;
	}
}
