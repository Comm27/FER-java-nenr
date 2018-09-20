package hr.fer.zemris.nenr.dz5.gui;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class DataGenerator {
	
	private static final int LETTERS = 5;

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		if (args.length != 3) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		Path path;
		int samplesPerLetter;
		int m;
		
		try {
			path = Paths.get(args[0]);
			samplesPerLetter = Integer.parseInt(args[1]);
			m = Integer.parseInt(args[2]);
			
		} catch (IllegalArgumentException e) {
			System.out.println("Ne valjaju parametri.");
			return;
		}
		
		List<LetterData> data = new ArrayList<>();
		
		for (int i = 0; i < LETTERS; i++) {
			LetterData letterData = new LetterData(getLetterCode(i, LETTERS));
			
			for (int j = 0; j < samplesPerLetter; j++) {
				System.out.println("Upišite " + (i + 1) + ". slovo, " + (j + 1) + ". put.");
				
				DrawingWindow drawingWindow = new DrawingWindow();
				
				SwingUtilities.invokeAndWait(() -> {
					drawingWindow.setVisible(true);
				});
				
				while (!drawingWindow.isFinishedDrawing());
				letterData.addLetterRepresentation(Util.processLetterPoints(drawingWindow.getCoordinates(), m));
			}
			
			data.add(letterData);
		}
		
		writeDataToFile(path, data);
	}

	private static double[] getLetterCode(int i, int letters) {
		double[] code = new double[letters];
		code[i] = 1.0;
		return code;
	}
	
	private static void writeDataToFile(Path path, List<LetterData> data) {
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			
			for (LetterData letterData : data) {
				for (double[][] matrix : letterData.getLetterRepresentations()) {
					
					for (int i = 0; i < matrix.length; i++) {
						bw.write(Double.toString(matrix[i][0]) + " " + Double.toString(matrix[i][1]) + "#");
					}
					
					bw.write("$");
					
					for (int i = 0; i < letterData.getLetterCode().length; i ++) {
						bw.write(Double.toString(letterData.getLetterCode()[i]) + " ");
					}
					
					bw.write(System.lineSeparator());
				}
			}
			
		} catch (IOException e) {
			System.out.println("Nije uspjelo pisanje podataka na disk.");
		}
	}
}
