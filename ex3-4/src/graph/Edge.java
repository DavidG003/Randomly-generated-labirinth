package graph;

import java.util.Objects;

/**
* Representation of an edge of a generic graph.  
* @author Georgiev David
* @param V generic type of the nodes
* @param L generic type of the label
*/
public class Edge<V, L> implements AbstractEdge<V, L>{
    private V sourceNode;
    private V destinationNode;
    private L label;

    /**
     * It creates a new instance of this class with the given parameters.
     * @param source : source node of the new edge
     * @param destination : destination node of the new edge
     * @param label : label of the new edge
     */
    public Edge(V source, V destination , L label){
        sourceNode = source;
        destinationNode = destination;
        this.label = label;
    }

    @Override
    public V getStart() {
        return sourceNode;
    }

    @Override
    public V getEnd() {
        return destinationNode;
    }

    @Override
    public L getLabel() {
        return label;
    }
    
    public void setStart(V newLabel) {
        sourceNode = newLabel;
    }

    public void setEnd(V newLabel) {
        destinationNode = newLabel;
    }

      @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return Objects.equals(sourceNode, edge.sourceNode) &&
               Objects.equals(destinationNode, edge.destinationNode); //&&
           //    Objects.equals(label, edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNode, destinationNode/* , label*/);
    }
    
}
