package hr.fer.zemris.nenr.dz7.genetic.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static Dataset loadDataset(String datasetName) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + datasetName),
								StandardCharsets.UTF_8))) {
			
			List<Sample> samples = new ArrayList<>();
			
			while (true) {
				String line = br.readLine();
				
				if (line == null) {
					break;
				}
				
				String[] args = line.trim().split("\\s+");
				
				samples.add(new Sample(
						Double.parseDouble(args[0]),
						Double.parseDouble(args[1]),
						Integer.parseInt(args[2]),
						Integer.parseInt(args[3]),
						Integer.parseInt(args[4])));
			}
			
			return new Dataset(samples);
			
		} catch (IOException | NullPointerException | NumberFormatException e) {
			return null;
		}
	}
}
