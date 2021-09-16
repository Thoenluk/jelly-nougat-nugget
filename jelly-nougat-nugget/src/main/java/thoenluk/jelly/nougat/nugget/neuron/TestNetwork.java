/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thoenluk.jelly.nougat.nugget.neuron;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class TestNetwork implements Comparable {
    
    private static final ValueCarrier ZERO = () -> {return 0;};
    private static final ValueCarrier ONE = () -> {return 1;};
    private static final ValueCarrier NEGATIVE_ONE = () -> {return -1;};
    private static final List<ValueCarrier> inputLayer = List.of(
                                ZERO, ONE, ZERO,
                                ONE, ONE, ONE,
                                ZERO, ONE, ZERO
                        );
    
    private static final double EPSILON = 0.000001D;
    
    private final List<Neuron> convolutionLayer;
    private final List<Neuron> reconstructionLayer;
    
    private boolean scoreOutdated;
    private Double score;
    
    public TestNetwork() {
        this.convolutionLayer = new ArrayList<>();
        this.reconstructionLayer = new ArrayList<>();
        scoreOutdated = true;
        score = null;
        
        for (int i = 0; i < 7; i++) {
            convolutionLayer.add(new Neuron(inputLayer));
        }
        
        for (int i = 0; i < 9; i++) {
            reconstructionLayer.add(new Neuron(convolutionLayer));
        }
    }
    
    public TestNetwork(TestNetwork parent) {
        this.convolutionLayer = new ArrayList<>();
        this.reconstructionLayer = new ArrayList<>();
        scoreOutdated = true;
        score = null;
        
        for (Neuron current : parent.convolutionLayer) {
            this.convolutionLayer.add(new Neuron(inputLayer, current));
        }
        
        for (Neuron current : parent.reconstructionLayer) {
            this.reconstructionLayer.add(new Neuron(this.convolutionLayer, current));
        }
    }
    
    public void mutateWeights(double range) {
        for (Neuron current : convolutionLayer) {
            current.mutateWeights(range);
        }
        
        for (Neuron current : reconstructionLayer) {
            current.mutateWeights(range);
        }
    }
    
    public void printValues() {
        for (int k = 0; k < 9; k++) {
            System.out.print(reconstructionLayer.get(k).getValue() + " ");
            if (k == 2 || k == 5 || k == 8) {
                System.out.println();
            }
        }
        System.out.println();
    }
    
    public Double getScore() {
        if (scoreOutdated) {
            calculateScore();
        }
        return score;
    }
    
    public void invalidateScore() {
        score = null;
    }
    
    private void calculateScore() {
        score = 0D;
        for (int i = 0; i < 9; i++) {
            score += Math.pow(2 + Math.abs(inputLayer.get(i).getValue() - reconstructionLayer.get(i).getValue()), 2);
        }
        scoreOutdated = false;
    }

    @Override
    public int compareTo(Object o) {
        TestNetwork o2 = (TestNetwork) o;
        double difference = getScore() - o2.getScore();
        if (difference < 0 - EPSILON) {
            return -1;
        } else if (difference > 0 + EPSILON) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "TestNetwork{" + "convolutionLayer=" + convolutionLayer + ", reconstructionLayer=" + reconstructionLayer + ", scoreOutdated=" + scoreOutdated + ", score=" + score + '}';
    }
}
