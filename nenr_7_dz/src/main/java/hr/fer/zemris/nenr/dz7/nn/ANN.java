package hr.fer.zemris.nenr.dz7.nn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ANN implements Cloneable {
	
	private static final double MIN_WEIGHT = -1d;
	private static final double MAX_WEIGHT = 1d;

	private List<Layer> layers;
	private Random rand;
	private int neuronCounter;
	
	public ANN(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		layers = new ArrayList<>();
		rand = new Random(System.currentTimeMillis());
		neuronCounter = Integer.MIN_VALUE;
		
		initLayersAndNeurons(neuronsPerLayer, activationFunctions);
	}
	
	public ANN(List<Layer> layers) {
		this.layers = layers;
		rand = new Random(System.currentTimeMillis());
	}
	
	private void initLayersAndNeurons(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		initInputLayer(neuronsPerLayer);
		initDistanceNeurons(neuronsPerLayer);
		initNormalNeurons(neuronsPerLayer, activationFunctions);
	}

	private void initInputLayer(int[] neuronsPerLayer) {
		Layer layer = new Layer();
		
		for (int j = 0, neurons = neuronsPerLayer[0]; j < neurons; j++) {	
			Neuron neuron = new Neuron(new Id(neuronCounter), 0d, new IdentityFunction());
			layer.addNeuron(neuron);
			neuronCounter++;
		}
		
		layers.add(layer);
	}
	
	private void initDistanceNeurons(int[] neuronsPerLayer) {
		Layer layer = new Layer();
		
		for (int j = 0, neurons = neuronsPerLayer[1]; j < neurons; j++) {	
			DistanceNeuron neuron;
			
			neuron = new DistanceNeuron(new Id(neuronCounter), 0d);
			neuronCounter++;
//			neuron.initBiasNeuron(new Id(neuronCounter));
//			neuronCounter++;
			
			layer.addNeuron(neuron);
		}
		
		layers.add(layer);
	}
	
	private void initNormalNeurons(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		for (int i = 2; i < neuronsPerLayer.length; i++) {
			Layer layer = new Layer();
			
			for (int j = 0, neurons = neuronsPerLayer[i]; j < neurons; j++) {	
				Neuron neuron = new Neuron(new Id(neuronCounter), 0d, activationFunctions[i - 2]);
				neuronCounter++;
				neuron.initBiasNeuron(new Id(neuronCounter));
				neuronCounter++;
				
				layer.addNeuron(neuron);
			}
			
			layers.add(layer);
		}
	}

	public List<Layer> getLayers() {
		return layers;
	}
	
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	
	public double[] calculateOutput(double[] input) {
		initInputLayerOutputs(input);
		calculateOtherLayersOutput();
		return generateOutput();
	}

	private void initInputLayerOutputs(double[] input) {
		Layer inputLayer = layers.get(0);
		
		for (int i = 0, size = inputLayer.size(); i < size; i++) {
			inputLayer.get(i).setOutput(input[i]);
		}
	}

	private void calculateOtherLayersOutput() {
		for (int i = 1, size = layers.size(); i < size; i++) {
			Layer receivingLayer = layers.get(i);
			
			for (int j = 0, neurons = receivingLayer.size(); j < neurons; j++) {
				receivingLayer.get(j).receiveSignal();
			}
		}
	}
	
	private double[] generateOutput() {
		Layer outputLayer = layers.get(layers.size() - 1);
		double[] output = new double[outputLayer.size()];
		
		for (int i = 0, size = output.length; i < size; i++) {
			output[i] = outputLayer.get(i).getOutput();
		}
		
		return output;
	}
	
	public int getWeightsCount() {
		return getWeights().length;
	}
	
	public double[] getWeights() {
		List<Double> weights = new ArrayList<>();
		
		for (Layer layer : layers) {
			for (Neuron neuron : layer) {
				for (Weight weight : neuron) {
					weights.add(weight.getValue());
				}
			}
		}
		
		return weights.stream().mapToDouble(d -> d.doubleValue()).toArray();
	}
	
	public void setWeights(double[] weights) {
		int i = 0;
		
		for (Layer layer : layers) {
			for (Neuron neuron : layer) {
				for (Weight weight : neuron) {
					weight.setValue(weights[i]);
					i++;
				}
			}
		}
	}
	
	public void fullyConnectFeedforward() {
		fullyConnectFeedforward(() -> getRandomWeight());
	}
	
	public void fullyConnectFeedforward(IWeightGetter weightGetter) {
		for (int i = 1, size = layers.size(); i < size; i++) {
			Layer transmittingLayer = layers.get(i - 1);
			Layer receivingLayer = layers.get(i);
			
			if (i > 1) {
				for (int j = 0, rSize = receivingLayer.size(); j < rSize; j++) {
					receivingLayer.get(j).setBiasWeight(weightGetter.getWeight());
				}
			}
			
			for (int j = 0, tSize = transmittingLayer.size(); j < tSize; j++) {
				for (int k = 0, rSize = receivingLayer.size(); k < rSize; k++) {
					Neuron.connectNeurons(
							transmittingLayer.get(j),
							receivingLayer.get(k),
							weightGetter.getWeight());
				}
			}
		}
	}
	
	private double getRandomWeight() {
		return rand.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
	}
	
	public ANN copy() {
		Map<Id, Neuron> idNeuronMap = new HashMap<>();
		
		List<Layer> layers = new ArrayList<>();
		for (Layer layer : this.layers) {
			layers.add(layer.copy(idNeuronMap));
		}
		
		return new ANN(layers);
	}
	
	public interface IWeightGetter {
		public double getWeight();
	}
}