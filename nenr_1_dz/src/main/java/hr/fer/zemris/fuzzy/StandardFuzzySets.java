package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

	public static IIntUnaryFunction lFunction(int alpha, int beta) {
		return number -> {
			if (number < alpha) {
				return 1;
			} else if (number >= alpha && number < beta) {
				return (double) (beta - number) / (beta - alpha);
			} else {
				return 0;
			}
		};
	}
	
	public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
		return number -> {
			if (number < alpha) {
				return 0;
			} else if (number >= alpha && number < beta) {
				return (double) (number - alpha) / (beta - alpha);
			} else {
				return 1;
			}
		};
	}
	
	public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
		return number -> {
			if (number < alpha) {
				return 0;
			} else if (number >= alpha && number < beta) {
				return (double) (number - alpha) / (beta - alpha);
			} else if (number >= beta && number < gamma) {
				return (double) (gamma - number) / (gamma - beta);
			} else {
				return 0;
			}
		};
	}
}
