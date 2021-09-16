package thoenluk.jelly.nougat.nugget.neuron;

import java.util.Objects;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Connection {
    private final ValueCarrier input;
    private double weight;
    
    public Connection(ValueCarrier input, double weight) {
        this.input = input;
        this.weight = weight;
    }
    
    public Connection(Connection original) {
        this.input = original.getInput();
        this.weight = original.getWeight();
    }

    public ValueCarrier getInput() {
        return input;
    }
    
    public double getValue() {
        return input.getValue() * weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.input);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Connection other = (Connection) obj;
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (!Objects.equals(this.input, other.input)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Connection{" + "input=" + input + ", weight=" + weight + '}';
    }
}
