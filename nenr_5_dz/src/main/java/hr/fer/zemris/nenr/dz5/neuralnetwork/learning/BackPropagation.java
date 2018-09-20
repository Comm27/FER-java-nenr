package hr.fer.zemris.nenr.dz5.neuralnetwork.learning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.nenr.dz5.neuralnetwork.ANN;
import hr.fer.zemris.nenr.dz5.neuralnetwork.Layer;
import hr.fer.zemris.nenr.dz5.neuralnetwork.Neuron;
import hr.fer.zemris.nenr.dz5.neuralnetwork.NeuronWeightPair;
import hr.fer.zemris.nenr.dz5.neuralnetwork.Weight;

public class BackPropagation {

	private IDataset dataset;
	private ANN ann;
	private int iterations;
	private double eta;
	
	public BackPropagation(IDataset dataset, ANN ann, int iterations, double eta) {
		this.dataset = dataset;
		this.ann = ann;
		this.iterations = iterations;
		this.eta = eta;
	}
	
	public void learn() {
		List<List<LearningSample>> batchGroups = dataset.getBatchGroups();
		
		int iteration = 0;
		while (iteration < iterations) {
			iteration++;
			
			for (List<LearningSample> learningSamples : batchGroups) {
				learnSingleBatchGroup(learningSamples);
			}
			
			printError(iteration);
		}
	}
	
	private void printError(int iteration) {
		double error = 0;
		
		for (List<LearningSample> learningSamples : dataset.getBatchGroups()) {
			for (LearningSample learningSample : learningSamples) {
				error += error(getSampleError(
									ann.calculateOutput(
										learningSample.getInput()),
										learningSample.getExpectedOutput()));
			}
		}
		
		System.out.println(iteration + " -> " + error / dataset.numberOfSamples());
	}

	private void learnSingleBatchGroup(List<LearningSample> learningSamples) {
		List<Layer> layers = ann.getLayers();
		Map<Weight, Double> weightToWeight = new HashMap<>();
		
		for (LearningSample learningSample : learningSamples) {
			Map<Neuron, Gradient> neuronsToGradients = new HashMap<>();
			
			double[] output = ann.calculateOutput(learningSample.getInput());
			double[] sampleError = getSampleError(output, learningSample.getExpectedOutput());
			
			initOuputLayerGradients(neuronsToGradients, layers, sampleError);
			initHiddenLayerGradients(neuronsToGradients, layers);
			updateWeightDeltas(weightToWeight, neuronsToGradients, layers);
		}
		
		updateWeights(weightToWeight);
	}
	
	private double[] getSampleError(double[] output, double[] expectedOutput) {
		double[] error = new double[output.length];
		
		for (int i = 0; i < output.length; i++) {
			error[i] = expectedOutput[i] - output[i];
		}
		
		return error;
	}
	
	private double error(double[] sampleError) {
		double error = 0;
		
		for (int i = 0; i < sampleError.length; i++) {
			error += sampleError[i] * sampleError[i];
		}
		
		return error / sampleError.length;
	}

	private void initOuputLayerGradients(Map<Neuron, Gradient> neuronsToGradients, List<Layer> layers,
			double[] sampleError) {
		Layer outputLayer = layers.get(layers.size() - 1);
		for (int i = 0, size = outputLayer.size(); i < size; i++) {
			Neuron neuron = outputLayer.get(i);
			neuronsToGradients.put(neuron, getGradient(neuron, sampleError[i]));
		}
	}
	
	private void initHiddenLayerGradients(Map<Neuron, Gradient> neuronsToGradients, List<Layer> layers) {
		for (int i = layers.size() - 2; i >= 0; i--) {
			Layer currentLayer = layers.get(i);
			
			for (Neuron neuron : currentLayer) {
				List<NeuronWeightPair> neuronsITranismittTo = neuron.getNeuronsITransmittTo();
				
				double gradientSum = 0;
				for (NeuronWeightPair neuronWeightPair : neuronsITranismittTo) {
					gradientSum += neuronWeightPair.getWeight().getValue() *
							neuronsToGradients.get(neuronWeightPair.getNeuron()).value;
				}
				
				neuronsToGradients.put(neuron, getGradient(neuron, gradientSum));
			}
		}
	}
	
	private void updateWeightDeltas(Map<Weight, Double> weightToWeight,
			Map<Neuron, Gradient> neuronsToGradients, List<Layer> layers) {
		
		for (Layer layer : layers) {
			for (Neuron neuron : layer) {
				for (NeuronWeightPair nwPair : neuron.getNeuronsIReceiveFrom()) {
					weightToWeight.merge(
							nwPair.getWeight(),
							neuronsToGradients.get(neuron).value * nwPair.getNeuron().getOutput(),
							(d1, d2) -> d1 + d2);
				}
			}
		}
	}
	
	private void updateWeights(Map<Weight, Double> weightToWeight) {
		weightToWeight.forEach((weight, gradSum) -> {
			weight.setValue(weight.getValue() + eta * gradSum);
		});
	}

	private Gradient getGradient(Neuron neuron, double error) {
		return new Gradient(neuron.getOutput() * (1 - neuron.getOutput()) * error);
	}

	private static class Gradient {
		
		private double value;

		public Gradient(double value) {
			this.value = value;
		}
	}
}