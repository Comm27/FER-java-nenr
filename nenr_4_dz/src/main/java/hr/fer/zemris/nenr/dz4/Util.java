package hr.fer.zemris.nenr.dz4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<double[]> loadDataset(String datasetName) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + datasetName),
								StandardCharsets.UTF_8))) {
			
			List<double[]> datasetList = new ArrayList<>();
			
			while (true) {
				String line = br.readLine();
				
				if (line == null) {
					break;
				}
				
				String[] args = line.trim().split("\\s+");
				double[] newInput = new double[args.length];
				
				for (int i = 0; i < args.length; i++) {
					newInput[i] = Double.parseDouble(args[i]);
				}
				
				datasetList.add(newInput);
			}
			
			return datasetList;
			
		} catch (IOException | NullPointerException | NumberFormatException e) {
			return null;
		}
	}
}
