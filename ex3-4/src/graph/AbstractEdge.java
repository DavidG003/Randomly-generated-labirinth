package graph;

/**
* Representation of an abstract edge.  
* @author Georgiev David
* @param V generic type of the nodes
* @param L generic type of the label
*/

public interface AbstractEdge<V,L> {
  /**
   * @return Source node of this edge
   */
    public V getStart();

    /**
     * @return Destination node of this edge
     */
    public V getEnd();

    /**
     * @return Label of this edge
     */ 
    public L getLabel(); // l'etichetta dell'arco
  }
  