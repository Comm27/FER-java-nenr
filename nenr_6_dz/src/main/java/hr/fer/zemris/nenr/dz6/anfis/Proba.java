package hr.fer.zemris.nenr.dz6.anfis;

public class Proba {

	public static void main(String[] args) {
		//System.out.println(Function.valueAt(2, 2));
		ANFIS anfis = new ANFIS(5, false, 10000, 0.0015);
		anfis.learn();
	}
}
