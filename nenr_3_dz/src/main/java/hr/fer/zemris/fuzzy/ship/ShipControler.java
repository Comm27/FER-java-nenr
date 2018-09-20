package hr.fer.zemris.fuzzy.ship;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.fuzzy.norms.ISNorm;
import hr.fer.zemris.fuzzy.norms.ITNorm;
import hr.fer.zemris.fuzzy.norms.MaxSNorm;
import hr.fer.zemris.fuzzy.norms.MinTNorm;
import hr.fer.zemris.fuzzy.system.IFuzzyControlSystem;

public class ShipControler {

	public static void main(String[] args) {
		
		ITNorm tNorm = new MinTNorm();
		ISNorm sNorm = new MaxSNorm();
		
		IFuzzyControlSystem steeringWheelCS =
				new ShipFuzzyControlSystem(
						new SteeringBaseOfKnowledge(sNorm, tNorm),
						new COADefuzzyfier(),
						sNorm);
		
		IFuzzyControlSystem accelerationCS =
				new ShipFuzzyControlSystem(
						new AccelerationBaseOfKnowledge(sNorm, tNorm),
						new COADefuzzyfier(),
						sNorm);
		
		try (
			BufferedReader br = new BufferedReader(
						new InputStreamReader(System.in, StandardCharsets.UTF_8));
			BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(System.out))) {
			
			while (true) {
				String line = br.readLine();
				if (line.trim().equals("KRAJ")) {
					break;
				}
				
				int[] values = parseLine(line);
				int acceleration = accelerationCS.getOutput(values);
				int steer = steeringWheelCS.getOutput(values);
				
				System.out.println(Integer.toString(acceleration) + " " + Integer.toString(steer));
				System.out.flush();
			}
		} catch (IOException e) {
		}
	}

	private static int[] parseLine(String line) {
		String[] args = line.trim().split("\\s+");
		
		int[] values = new int[args.length];
		
		for (int i = 0; i < args.length; i++) {
			values[i] = Integer.parseInt(args[i]);
		}
		
		return values;
	}
}
