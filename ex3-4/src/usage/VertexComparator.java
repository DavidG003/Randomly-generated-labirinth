package usage;

import java.util.Comparator;

/**
 * A comparator used to compare two {@link WeightedVertex} objecs by confronting their weight attributes
 */
public class VertexComparator<V> implements Comparator<WeightedVertex<V>> {

    @Override
    public int compare(WeightedVertex<V> vertex1, WeightedVertex<V> vertex2) {
        if(vertex1.equals(vertex2)){
            return 0;
        }

        if (vertex1.getWeight().doubleValue() < vertex2.getWeight().doubleValue()) {
            return -1;
        } 
        //if (vertex1.getWeight() >= vertex2.getWeight()) {
            return 1;
        
    }
}