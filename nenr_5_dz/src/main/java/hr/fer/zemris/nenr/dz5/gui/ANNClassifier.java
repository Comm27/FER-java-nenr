package hr.fer.zemris.nenr.dz5.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import hr.fer.zemris.nenr.dz5.neuralnetwork.ANN;
import hr.fer.zemris.nenr.dz5.neuralnetwork.IActivationFunction;
import hr.fer.zemris.nenr.dz5.neuralnetwork.SigmoidFunction;
import hr.fer.zemris.nenr.dz5.neuralnetwork.learning.BackPropagation;

public class ANNClassifier {
	
	private static final List<LetterCodePair> LETTER_CODE_PAIRS;
	private static final int BATCH = 1;
	private static final int STOHASTIC = 2;
	
	static {
		LETTER_CODE_PAIRS = new ArrayList<>();
		LETTER_CODE_PAIRS.add(new LetterCodePair(new double[]{ 1.0, 0.0, 0.0, 0.0, 0.0 }, "alpha"));
		LETTER_CODE_PAIRS.add(new LetterCodePair(new double[]{ 0.0, 1.0, 0.0, 0.0, 0.0 }, "beta"));
		LETTER_CODE_PAIRS.add(new LetterCodePair(new double[]{ 0.0, 0.0, 1.0, 0.0, 0.0 }, "gamma"));
		LETTER_CODE_PAIRS.add(new LetterCodePair(new double[]{ 0.0, 0.0, 0.0, 1.0, 0.0 }, "delta"));
		LETTER_CODE_PAIRS.add(new LetterCodePair(new double[]{ 0.0, 0.0, 0.0, 0.0, 1.0 }, "eta"));
	}

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		ANN ann = getLearnedANN(args);
		if (ann == null) {
			return;
		}
		
		int m = Integer.parseInt(args[1]);
		askUserToDrawLetters(ann, m);
	}

	private static ANN getLearnedANN(String[] args) {
		try {
			int m = Integer.parseInt(args[1]);
			int algorithm = Integer.parseInt(args[3]);
			
			List<LetterData> letterData = Util.getLetterData(args[0], m);
			int[] architecture = Util.getArchitecture(args[2], m, letterData.get(0).getLetterCode().length);
			
			if (letterData == null || architecture == null) {
				throw new NullPointerException();
			}
			
			ANN ann = createANN(architecture);
			learnANN(ann, letterData, m, algorithm);
			return ann;
			
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println("Ne validni parametri.");
			return null;
		}
	}
	
	private static ANN createANN(int[] architecture) {
		IActivationFunction[] activationFunctions = new IActivationFunction[architecture.length - 1];
		for (int i = 0; i < activationFunctions.length; i++) {
			activationFunctions[i] = new SigmoidFunction();
		}
		
		ANN ann = new ANN(architecture, activationFunctions);
		ann.fullyConnectFeedforward();
		
		return ann;
	}

	private static void learnANN(ANN ann, List<LetterData> letterData, int m, int algorithm) {
		int batchSize;
		
		if (algorithm == BATCH) {
			batchSize = letterData.size() * letterData.get(0).getLetterRepresentations().size();
		} else if (algorithm == STOHASTIC) {
			batchSize = 1;
		} else {
			batchSize = 10;
		}
		
		BackPropagation bp =
				new BackPropagation(
						new DrawingDataset(letterData, batchSize),
						ann,
						500,
						0.1);
		bp.learn();
	}
	
	private static void askUserToDrawLetters(ANN ann, int m) {
		while (true) {
			DrawingWindow drawingWindow = new DrawingWindow();
			
			SwingUtilities.invokeLater(() -> {
				drawingWindow.setVisible(true);
			});
			
			while (!drawingWindow.isFinishedDrawing());
			
			try {
				double[] output = ann.calculateOutput(Util.transform(Util.processLetterPoints(drawingWindow.getCoordinates(), m)));
				printOutput(Arrays.copyOf(output, output.length), roundOutput(output));
			} catch (Exception e) {
				System.out.println("Premalo upisanih znakova.");
				continue;
			}
		}
	}
	
	private static void printOutput(double[] output, double[] roundOutput) {
		for (LetterCodePair letterCodePair : LETTER_CODE_PAIRS) {
			if (letterCodePair.hasCode(roundOutput)) {
				StringBuilder sb = new StringBuilder();
				sb.append(letterCodePair.getLetter() + " -> " + formatArray(output));
				System.out.println(sb.toString());
				return;
			}
		}
		
		System.out.println("Nije pronađen ni jedan uzorak -> " + formatArray(output));
	}
	
	private static double[] roundOutput(double[] output) {
		for (int i = 0; i < output.length; i++) {
			output[i] = output[i] >= 0.5 ? 1.0 : 0.0;
		}
		
		return output;
	}
	
	private static String formatArray(double[] output) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.####");
		
		for (double d : output) {
			sb.append(df.format(d) + ", ");
		}
		
		return sb.toString();
	}

	private static class LetterCodePair {
		
		private double[] code;
		private String letter;
		
		public LetterCodePair(double[] code, String letter) {
			this.code = code;
			this.letter = letter;
		}

		public boolean hasCode(double[] code) {
			return Arrays.equals(this.code, code);
		}
		
		public String getLetter() {
			return letter;
		}
	}
}
