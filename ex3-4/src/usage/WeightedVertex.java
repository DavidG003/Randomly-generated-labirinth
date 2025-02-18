package usage;

import java.util.Objects;

/**
 * Object that represents a vertex of a graph and it takes note of two additional attributes to know the current {@code weight} of the edge between {@code parentVertex} and {@code vertex}.
 * @param <V> : the generic type of a vertex.
 */
public class WeightedVertex<V> {
    private V vertex;
    private V parentVertex;
    private Number weight;

    /**
     * It creates a new WeightedVertex object that has infinite weight and no parent
     * @param vertex : the vertex that identifies this object
     */
    public WeightedVertex(V vertex){
        this.vertex = vertex;
        this.weight = Double.POSITIVE_INFINITY;
        this.parentVertex = null;
    }
/**
 * 
 * @return the vertex that identifies this object
 */
    public V getVertex(){
        return this.vertex;
    }

    /**
     * @return the weight of the edge between {@code parentVertex} and {@code vertex}. 
     */
    public Number getWeight(){
        return this.weight;
    }

    /**
     * @return the vertex that is a neighbour to {@code vertex}.
     */
    public V getParent(){
        return this.parentVertex;
    }

/**
 * @param newVert : new vertex that overwrites the last one
 */
    public void setVertex(V newVert){
        this.vertex = newVert;
    }

/**
 * @param newWeight : new weight that overwrites the last one
 */
    public void setWeight(Number newWeight){
        this.weight = newWeight;
    }
    
/**
 * @param newParent : new parentVertex that overwrites the last one
 */
    public void setParent(V newParent){
        this.parentVertex= newParent;
    }

        
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WeightedVertex<?> that = (WeightedVertex<?>) obj;
        return Objects.equals(vertex, that.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex);
    }

}
