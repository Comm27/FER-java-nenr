package hr.fer.zemris.nenr.dz7.zadaci;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Zadatak2 {

	public static void main(String[] args) {
		
		List<Pair> g1 = new ArrayList<>();
		List<Pair> g2 = new ArrayList<>();
		List<Pair> g3 = new ArrayList<>();
		
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(Zadatak2.class.getResourceAsStream("/" + "zad7-dataset.txt")))) {
			
			String line;
			
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] numbers = line.split("\\s+");
				double d1 = Double.parseDouble(numbers[0]);
				double d2 = Double.parseDouble(numbers[1]);
				int i1 = Integer.parseInt(numbers[2]);
				int i2 = Integer.parseInt(numbers[3]);
				int i3 = Integer.parseInt(numbers[4]);
				
				if (i1 == 1) {
					g1.add(new Pair(d1, d2));
				} else if (i2 == 1) {
					g2.add(new Pair(d1, d2));
				} else if (i3 == 1) {
					g3.add(new Pair(d1, d2));
				}
			}
			
			
		} catch (IOException e) {
			System.out.println("Ne mogu obaviti čitanje.");
			return;
		}
		
		writeGroupToFile(g1, "zad_2_g1.txt");
		writeGroupToFile(g2, "zad_2_g2.txt");
		writeGroupToFile(g3, "zad_2_g3.txt");
	}
	
	private static void writeGroupToFile(List<Pair> group, String fileName) {
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
			
			for (Pair pair : group) {
				bw.write(pair.d1 + " " + pair.d2 + System.lineSeparator());
			}
			
		} catch (IOException e) {
			System.out.println("Ne mogu pisati u file.");
			return;
		}
	}

	private static class Pair {
		
		private double d1;
		private double d2;
		
		public Pair(double d1, double d2) {
			this.d1 = d1;
			this.d2 = d2;
		}
	}
}