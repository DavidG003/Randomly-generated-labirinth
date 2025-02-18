package graph;

import java.util.Collection;

/**
* Representation of an abstract graph.  
* @author Georgiev David
* @param V generic type of any node in the graph
* @param L generic type of any edge label in the graph
*/
  
public interface AbstractGraph<V,L> {

    /**
     * @return true if this graph is directed, false otherwise
     */
    public boolean isDirected();

    /**
     * 
     * @return true if this graph is labelled, false otherwise
     */
    public boolean isLabelled();

    /**
     * It adds a node to this graph, it can fail if the node to add already exist in the graph.
     * @param a : new node to add
     * @return true if the node was successfully added to this graph, false otherwise
     */
    public boolean addNode(V a);

    /**
     * It adds an edge to this graph, if the specified edge already exists the graph is not modified. <p> 
     * <b>Note: </b> When this graph is labelled the existance of a node in this graph is determined only by the source and the destination node,
     * this implies that a label cannot be directly modified after its creation.
     * @param a : specifies the source node of the edge
     * @param b : specifies the destination node of the edge
     * @param l : specifies the label of the edge, if this graph is not labelled this param is ignored
     * @return true if the edge was successfully added to this graph, false otherwise
     */
    public boolean addEdge(V a, V b, L l);

    /**
     * It determines if a given node already exists in this graph.
     * @param a : node whose existence to check
     * @return true if the specified node exists, false otherwise
     */
    public boolean containsNode(V a);

    /**
     * It determines if an edge exists in this graph, if this graph is not directed then the order in which the parameters are given is not important.
     * @param a : source node of the edge
     * @param b : destination node of the edge to search
     * @return true if the edge exists in this graph, false otherwise
     */
    public boolean containsEdge(V a, V b);

    /**
     * It removes a node and all of the edges that are connected to it from this graph. If the node doesn't exist in this graph this method doesn't modify the graph.
     * @param a : node to remove from this graph
     * @return true if the node was successfully removed, false otherwise.
     */
    public boolean removeNode(V a);

    /**
     * It removes an edge from this graph, if this graph is not directed then the order in which the parameters are given is not important.
     * @param a : source node of the edge to delete
     * @param b : destination node of the edge to delete
     * @return true if the edge was successfully deleted, false otherwise.
     */
    public boolean removeEdge(V a, V b);

    /**
     * @return number of nodes that this graph contains.
     */
    public int numNodes();

    /**
     * @return number of edges that this graph contains.
     */
    public int numEdges();

    /**
     * It creates a new {@link Collection} where it stores all the nodes of this graph, ignoring all the edges between the nodes.
     * @return a collection of all the nodes of this graph. 
     */
    public Collection<V> getNodes();

    /**
     * It creates a new {@link Collection} where all the edges of this graph, with their corresponding labels, are stored in.
     * @return a collection that rapresents all the edges of this graph.
     */
    public Collection<? extends AbstractEdge<V,L> > getEdges();

    /**
     * It creates a new {@link Collection} where all the neighboring nodes of a given node are stored. 
     * @param a : node whose neighboring nodes are searched for. 
     * @return a collection of all the neighboring nodes of a given node in this graph. If the node doesn't exist in this graph then it returns null.
     */
    public Collection<V> getNeighbours(V a);

    /**
     * It retrieves the label of a given node in this graph. If this graph is not directed then the order in which the parameters are given is not important
     * @param a : source node of the edge from which to get the label
     * @param b : destination node of the edge from which to get the label
     * @return label of the specified edge, if the graph is not labbeled or the edge doesn't exist then it returns null
     */
    public L getLabel(V a, V b); // recupero dell'etichetta di un arco -- O(1) (*)
  }
  