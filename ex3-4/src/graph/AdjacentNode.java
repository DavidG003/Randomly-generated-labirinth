package graph;

import java.util.Objects;

/**
* Partial representation of a generic edge in a generic graph. <p>
* <b>Important notes: </b>
*<p>
*  -Without a source node to associate it with, this object has no default meaning. <p>
*  -Method equals has been overwritten for this class as two instances are equal only when their node attributes are equal without giving any importance to the label when confonting these two.
* @author Georgiev David
* @param V generic type of the node
* @param L generic type of the label
*/
public class AdjacentNode<V,L> {
    private V node;
    private L label;

    /**
     * It creates a new AdjacentNode with the given node and label value
     * @param node : destination node name of the new edge
     * @param label : label value of the new edge
     */
    AdjacentNode(V node, L label){
        this.node = node;
        this.label = label;
    }

    
    /**
     * @return node name of this AdjacentNode 
     */
    public V getNode(){
        return node;
    }

    /**
     * @return lavel value of this AdjacentNode 
     */
    public L getLabel(){
        return label;
    }
    
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacentNode<?, ?> that = (AdjacentNode<?, ?>) obj;
        return Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }

}
