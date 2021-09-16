package thoenluk.jelly.nougat.nugget.neuron;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Neuron implements ValueCarrier {
    
    private boolean valueOutdated;
    private double value;
    private List<Connection> inputs;
    
    public Neuron(ValueCarrier... inputLayer) {
        this(Arrays.asList(inputLayer));
    }
    
    public Neuron(List<? extends ValueCarrier> inputs) {
        this.valueOutdated = true;
        this.inputs = new LinkedList<>();
        for (ValueCarrier current : inputs) {
            this.inputs.add(new Connection(current, 0));
        }
    }
    
    public Neuron(List<? extends ValueCarrier> inputs, Neuron original) {
        this.valueOutdated = true;
        this.inputs = new LinkedList<>();
        List<Connection> originalConnections = original.inputs;
        for (int i = 0; i < inputs.size(); i++) {
            this.inputs.add(new Connection(inputs.get(i), originalConnections.get(i).getWeight()));
        }
    }
    
    public Neuron(Connection... inputs) {
        this.valueOutdated = true;
        this.inputs = Arrays.asList(inputs);
    }
       
    @Override
    public double getValue() {
        if (valueOutdated) {
            calculateValue();
        }
        return value;
    }
    
    /**
     * Change the weights of inputs on this neuron by +- range at random, with uniform distribution.
     * Weights are scaled back to [-1, 1] after.
     * 
     * @param range The maximum deviation in either direction. 
     */
    public void mutateWeights(double range) {
        for (Connection current : inputs) {
            double weight = current.getWeight();
            weight += (Math.random() - 0.5) * range * 2;
            if (weight < -1) {
                weight = -1;
            } else if (weight > 1) {
                weight = 1;
            }
            current.setWeight(weight);
        }
        outdateValue();
    }
    
    public void outdateValue() {
        valueOutdated = true;
    }
        
    private void calculateValue() {
        value = 0;
        double totalWeights = 0;
        for (Connection current : inputs) {
            value += current.getValue();
            totalWeights += Math.abs(current.getWeight());
        }
        value /= totalWeights;
        valueOutdated = false;
    }

    @Override
    public String toString() {
        return "Neuron{" + "valueOutdated=" + valueOutdated + ", value=" + value + ", inputs=" + inputs + '}';
    }
}
