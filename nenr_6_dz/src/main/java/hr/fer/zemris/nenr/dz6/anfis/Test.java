package hr.fer.zemris.nenr.dz6.anfis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		ANFIS anfis = new ANFIS(5, true, 10000, 0.000001);
		anfis.learn();
		printRuleParameters(anfis);
		writeErrorSurfaceToFile(anfis);
		writeEpochsToFile(false, "stohastic_errors.txt");
		writeEpochsToFile(true, "batch_errors.txt");
	}

	private static void writeEpochsToFile(boolean batch, String file) {
		ANFIS anfis = new ANFIS(5, batch, 10000, 0.001);
		anfis.learn();
		
		List<double[]> epochErrors = anfis.getEpochErrors();
		
		try (BufferedWriter bw =
				new BufferedWriter(
						new OutputStreamWriter(
								Files.newOutputStream(Paths.get(file))))) {
			
			DecimalFormat df = new DecimalFormat("#####.####");
			
			for (double[] epoch : epochErrors) {
				bw.write(
						df.format(epoch[0]).replaceAll(",", ".") + " " +
						df.format(epoch[1]).replaceAll(",", ".") + " " +
						System.lineSeparator());
			}
			
		} catch (IOException e) {
			System.out.println("Greška pri pisanju u datoteku.");
		}
	}

	private static void printRuleParameters(ANFIS anfis) {
		for (Rule rule : anfis.getRules()) {
			System.out.println(rule.getA() + ", " + rule.getB() + ", " + rule.getC() + ", " + rule.getD());
		}
	}

	private static void writeErrorSurfaceToFile(ANFIS anfis) {
		try (BufferedWriter bw =
				new BufferedWriter(
						new OutputStreamWriter(
								Files.newOutputStream(Paths.get("error_surface.txt"))))) {
			
			List<double[]> points = anfis.get3DErrors();
			DecimalFormat df = new DecimalFormat("##.####");
			
			for (double[] point : points) {
				bw.write(
						df.format(point[0]).replaceAll(",", ".") + " " +
						df.format(point[1]).replaceAll(",", ".") + " " +
						df.format(point[2]).replaceAll(",", ".") + " " +
						System.lineSeparator());
			}
			
		} catch (IOException e) {
			System.out.println("Greška pri pisanju u datoteku.");
		}
	}
}
