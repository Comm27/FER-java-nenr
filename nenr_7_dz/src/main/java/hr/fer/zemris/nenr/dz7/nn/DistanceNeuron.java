package hr.fer.zemris.nenr.dz7.nn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class DistanceNeuron extends Neuron {

	private Map<Neuron, Weight> scalers;
	private static Random rand = new Random(System.currentTimeMillis());
	
	public DistanceNeuron(Id id, double output) {
		super(id, output, null);
		scalers = new HashMap<>();
	}
	
	@Override
	void addToNeuronsIReceiveFrom(Neuron neuron, Weight w) {
		super.addToNeuronsIReceiveFrom(neuron, w);
		scalers.put(neuron, new Weight(rand.nextGaussian()));
	}
	
	@Override
	public void receiveSignal() {
		double sum = 0;
		
		for (NeuronWeightPair neuronWeightPair : neuronsIReceiveFrom) {
			Weight scalingWeight = scalers.get(neuronWeightPair.getNeuron());
			sum += Math.abs(neuronWeightPair.getNeuron().getOutput() - neuronWeightPair.getWeight().getValue()) /
					Math.abs(scalingWeight.getValue());
		}
		
		setOutput(1.0 / (1.0 + sum));
	}
	
	@Override
	public Iterator<Weight> iterator() {
		return new NeuronIterator();
	}
	
	private class NeuronIterator implements Iterator<Weight> {
		
		private int index;
		
		public NeuronIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < neuronsIReceiveFrom.size() * 2;
		}

		@Override
		public Weight next() {
			index++;
			
			if ((index - 1) % 2 == 0) {
				return neuronsIReceiveFrom.get((index - 1) / 2).getWeight();
			}
			
			//Neuron n = neuronsIReceiveFrom.get((index - 2) / 2);
			
			return scalers.get(neuronsIReceiveFrom.get((index - 2) / 2).getNeuron());
		}
	}
}
