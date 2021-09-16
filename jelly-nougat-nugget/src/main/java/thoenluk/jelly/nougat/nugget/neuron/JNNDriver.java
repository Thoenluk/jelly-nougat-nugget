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
public class JNNDriver {
    public static void main(String[] args) {
        int i, parentIndex, childIndex;
        
        List<TestNetwork> networks = new ArrayList<>(1010);
        List<TestNetwork> nextGeneration;
        TestNetwork newNetwork, current;
        
        for (i = 0; i < 1010; i++) {
            newNetwork = new TestNetwork();
            newNetwork.mutateWeights(1);
            networks.add(newNetwork);
        } 
        
        for (i = 1; i < 500; i++) {
            networks.sort(null);
            
            if (i % 100 == 0) {
                networks.get(0).printValues();
            }
            
            nextGeneration = new ArrayList(1010);
            
            for (parentIndex = 0; parentIndex < 10; parentIndex++) {
                nextGeneration.add(networks.get(parentIndex));
            }
            for (parentIndex = 0; parentIndex < 10; parentIndex++) {
                for (childIndex = 0; childIndex < 100; childIndex++) {
                    current = nextGeneration.get(parentIndex);
                    newNetwork = new TestNetwork(current);
                    newNetwork.mutateWeights(1F / i);
                    nextGeneration.add(newNetwork);
                }
            }
            networks = nextGeneration;
        }
        
        System.out.println(networks.get(0).getScore());
    }
}
