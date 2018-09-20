package hr.fer.zemris.nenr.dz7.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractGeneticAlgorithm<T> implements IOptAlgorithm<T> {

	IPopulation<T> population;
	ISelectionOperator<T> selectionOperator;
	ICrossoverOperator<T> crossoverOperators[];
	IMutationOperator<T> mutationOperators[];
	IProblem<T> problem;
	
	Random rand;
	int populationSize;
	double fitnessThreshold;
	int maxGenerations;
	int iteration;
	double mutationChooser;
	
	public AbstractGeneticAlgorithm(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T>[] crossoverOperators,
			IMutationOperator<T>[] mutationOperators,
			double mutationChooser,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		this.populationSize = populationSize;
		this.selectionOperator = selectionOperator;
		this.crossoverOperators = crossoverOperators;
		this.mutationOperators = mutationOperators;
		this.mutationChooser = mutationChooser;
		this.problem = problem;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		
		this.population = createInitialPopulation(populationSize);
		rand = new Random();
	}

	IPopulation<T> createInitialPopulation(int populationSize) {
		IPopulation<T> population = new Population<>();
		
		while (population.getSize() < populationSize) {
			population.add(problem.generateRandomSolution());
		}
		
		return population;
	}
	
	void resetPopulation() {
		population = createInitialPopulation(populationSize);
	}
	
	void setPopulation(IPopulation<T> population) {
		this.population = population;
	}

	@Override
	public ISolution<T> run() {
		iteration = 0;
		
		while (iteration < maxGenerations) {
			if (foundGoodSolution()) {
				return population.getBestSolution();
			}
			
			population = makeNewPopulation();
			iteration++;
			
			if (printSolution(population.getBestSolution())) {
				printToStdOutput();
			}
		}
		
		return population.getBestSolution();
	}

	public ISolution<T> getBestSolution() {
		return population.getBestSolution();
	}
	
	public IPopulation<T> getPopulation() {
		return population;
	}
	
	boolean foundGoodSolution() {
		return evaluatePopulation() >= fitnessThreshold;
	}

	double evaluatePopulation() {
		return population.getBestSolution().getFitness();
	}
	
	void printToStdOutput() {
		System.out.println(iteration + " -> " + population.getBestSolution());
	}
	
	ISolution<T> getFirstParent(ISelectionOperator<T> selectionOperator) {
		return selectionOperator.select(population);
	}
	
	ISolution<T> getNextParent(ISelectionOperator<T> selectionOperator, @SuppressWarnings("unchecked") ISolution<T> ... parents) {
		List<ISolution<T>> forbiddenSolutions = new ArrayList<>();
		
		for (ISolution<T> parent : parents) {
			forbiddenSolutions.add(parent);
		}
		
		return selectionOperator.select(population, forbiddenSolutions);
	}
	
	ISolution<T> getChild(
			ICrossoverOperator<T>[] crossoverOperators,
			IMutationOperator<T>[] mutationOperators,
			ISolution<T> parent1,
			ISolution<T> parent2) {
		
		ISolution<T> child = crossoverOperators[rand.nextInt(crossoverOperators.length)].crossover(parent1, parent2);
		
		if (rand.nextDouble() < mutationChooser) {
			child = mutationOperators[0].mutate(child);
		} else {
			child = mutationOperators[1].mutate(child);
		}
		
		setChildProperties(child);
		return child;
	}
	
	void setChildProperties(ISolution<T> child) {
		child.setFitness(problem.getFitness(child));
		child.setValue(problem.getValue(child));
	}
	
	abstract IPopulation<T> makeNewPopulation();
	abstract boolean printSolution(ISolution<T> solution);
}
