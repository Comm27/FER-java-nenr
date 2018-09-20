package hr.fer.zemris.nenr.dz7.genetic;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
