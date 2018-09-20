package hr.fer.zemris.nenr.dz5.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<LetterData> getLetterData(String fileName, int m) {
	
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + fileName),
								StandardCharsets.UTF_8))) {
			
			List<LetterData> letterData = new ArrayList<>();
			
			String line;
			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				
				String[] args = line.split("[$]");
				String[] coords = args[0].trim().split("#");
				if (coords.length != m) {
					return null;
				}
				
				String[] code = args[1].trim().split("\\s+");
				
				LetterData singleData = constructSingleLetterData(coords, code, m);
				if (singleData == null) {
					return null;
				}
				
				merge(letterData, singleData);
			}
			
			return letterData;
			
		} catch (IOException e) {
			return null;
		}
	}

	private static LetterData constructSingleLetterData(String[] coords, String[] code, int m) {
		try {
			double[] letterCode = new double[code.length];
			for (int i = 0; i < letterCode.length; i++) {
				letterCode[i] = Double.parseDouble(code[i]);
			}
			
			LetterData letterData = new LetterData(letterCode);
			double[][] matrix = new double[m][2];
			
			for (int i = 0; i < coords.length; i++) {
				String[] singleCoords = coords[i].split("\\s+");
				matrix[i][0] = Double.parseDouble(singleCoords[0]);
				matrix[i][1] = Double.parseDouble(singleCoords[1]);
			}
			
			letterData.addLetterRepresentation(matrix);
			return letterData;
		} catch (NumberFormatException | NullPointerException e) {
			return null;
		}
	}
	
	private static void merge(List<LetterData> letterData, LetterData singleData) {
		if (!letterData.contains(singleData)) {
			letterData.add(singleData);
			return;
		}
		
		LetterData ld = letterData.get(letterData.indexOf(singleData));
		for (double[][] matrix : singleData.getLetterRepresentations()) {
			ld.addLetterRepresentation(matrix);
		}
	}

	public static int[] getArchitecture(String string, int m, int codeLength) {
		try {
			String[] args = string.split("x");
			int[] architecture = new int[args.length];
			
			for(int i = 0; i < args.length; i++) {
				architecture[i] = Integer.parseInt(args[i]);
			}
			
			if (architecture[0] != 2 * m || architecture[architecture.length - 1] != codeLength) {
				return null;
			}
			
			return architecture;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static double[][] processLetterPoints(List<Coordinate> coordinates, int m) {
		scalePointsWithMean(coordinates);
		scalePointsWithMaximumCoordinate(coordinates);
		
		double length = calculateLength(coordinates);
		List<Coordinate> representatives = getRepresentatives(coordinates, length, m);
	
		return representativesToMatrix(representatives);
	}
	
	private static void scalePointsWithMean(List<Coordinate> coordinates) {
		double xMean = coordinates.stream().mapToDouble(coord -> coord.getX()).average().getAsDouble();
		double yMean = coordinates.stream().mapToDouble(coord -> coord.getY()).average().getAsDouble();
		
		coordinates.forEach(coord -> {
			coord.setX(coord.getX() - xMean);
			coord.setY(coord.getY() - yMean);
		});
	}
	
	private static void scalePointsWithMaximumCoordinate(List<Coordinate> coordinates) {
		double xMax = coordinates.stream().mapToDouble(coord -> Math.abs(coord.getX())).max().getAsDouble();
		double yMax = coordinates.stream().mapToDouble(coord -> Math.abs(coord.getY())).max().getAsDouble();
		double max = xMax > yMax ? xMax : yMax;
		
		for (Coordinate coord : coordinates) {
			coord.setX(coord.getX() / max);
			coord.setY(coord.getY() / max);
		}
	}
	
	private static double calculateLength(List<Coordinate> coordinates) {
		double length = 0;
		
		for (int i = 1; i < coordinates.size(); i++) {
			Coordinate c1 = coordinates.get(i - 1);
			Coordinate c2 = coordinates.get(i);
			length += coordinatesDistance(c1, c2);
		}
		
		return length;
	}

	private static double coordinatesDistance(Coordinate c1, Coordinate c2) {
		return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
	}
	
	private static List<Coordinate> getRepresentatives(List<Coordinate> coordinates, double length, int m) {
		double twoPointsSpace = length / m;
		List<Coordinate> representatives = new ArrayList<>();
		representatives.add(coordinates.get(0));
		
		for (int i = 1; i < m; i++) {
			double targetDistance = i * twoPointsSpace;
			double currentDistance = 0;
			
			for (int j = 1; j < coordinates.size() - 1; j++) {
				Coordinate first = coordinates.get(j - 1);
				Coordinate next = coordinates.get(j);
				
				currentDistance += coordinatesDistance(first, next);
				if (currentDistance > targetDistance) {
					representatives.add(next);
					break;
				}
			}
		}
		
		return representatives;
	}
	
	private static double[][] representativesToMatrix(List<Coordinate> representatives) {
		double[][] matrix = new double[representatives.size()][2];
		
		for (int i = 0; i < representatives.size(); i++) {
			matrix[i][0] = representatives.get(i).getX();
			matrix[i][1] = representatives.get(i).getY();
		}
		
		return matrix;
	}
	
	public static double[] transform(double[][] matrix) {
		double[] singleRow = new double[matrix.length * matrix[0].length];
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				singleRow[i * matrix[i].length + j] = matrix[i][j];
			}
		}
		
		return singleRow;
	}
}
