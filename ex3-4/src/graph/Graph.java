package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Object that represents a graph composed by nodes and edges.
 * @param <V> type used to represent the nodes of the graph 
 * @param <L> type used to represent the edges of the graph 
 * @author David Georgievs
 */

public class Graph<V, L> implements AbstractGraph<V, L> {

    private boolean directed;
    private boolean labelled;
    private int totalEdges;
    private HashMap<V, LinkedList<AdjacentNode<V, L>>> adjacentMappedList;

    /**
     * Creates a new graph object, 
     * @param directed : used to detrermine if the new graph is directed
     * @param labelled : used to detrermine if the new graph is labelled
     */
    public Graph(boolean directed, boolean labelled) {
        this.totalEdges = 0;
        this.adjacentMappedList = new HashMap<>();
        this.directed = directed;
        this.labelled = labelled;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isLabelled() {
        return labelled;
    }

    public boolean containsNode(V a) {
        return adjacentMappedList.containsKey(a);
    }

    public boolean containsEdge(V a, V b) {
        if(!this.containsNode(a) || !this.containsNode(b)) return false;
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);

        for (AdjacentNode<V, L> adjacent : adjListA) {
            if (adjacent.getNode().equals(b)) {
                return true;
            }
        }
        return false;
    }

    public boolean addNode(V a) {
        if (adjacentMappedList.containsKey(a)) {
            return false;
        }

        LinkedList<AdjacentNode<V, L>> adjList = new LinkedList<AdjacentNode<V, L>>();
        adjacentMappedList.put(a, adjList);
        return true;
    }

    public boolean addEdge(V a, V b, L l) {
        boolean existentialCond =   !adjacentMappedList.containsKey(a) ||
                                    !adjacentMappedList.containsKey(b) ||
                                    (isLabelled() && l == null)        ||
                                    (!isLabelled() && l != null);

        if (existentialCond || this.containsEdge(a, b))
            return false;
    
        secureEdgeAdd(a, b, l);
  
        if (!isDirected()) 
            secureEdgeAdd(b, a, l);

        totalEdges++;
        return true;
    }

    public boolean removeEdge(V a, V b) {
        if (!adjacentMappedList.containsKey(b) || !adjacentMappedList.containsKey(a)) {
            return false;
        }
        boolean removed = false;
        removed = safeEdgeRemove(a, b);
        if (removed) {
            if (!directed) {
                removed = safeEdgeRemove(b, a);
            }
            totalEdges--;
        }

        return removed;
    }

    public int numNodes() {
        return adjacentMappedList.size();
    }

    public int numEdges() {
        return totalEdges;
    }

    public boolean removeNode(V a) {

        if (!adjacentMappedList.containsKey(a))
            return false;

        if (!isDirected()) {
            notDirectedEdgesRemove(a);
            eraseNode(a);

        } else {
            eraseNode(a);
            directedEdgesRemove(a);
        }

        return true;
    }

    public Collection<V> getNodes() {
        Collection<V> nodes = new ArrayList<>(adjacentMappedList.keySet());
        return nodes;
    }

    public L getLabel(V a, V b) {
        if (!adjacentMappedList.containsKey(b) || !adjacentMappedList.containsKey(a) || !isLabelled()) {
            return null;
        }
        
        AdjacentNode<V, L> foundEdge = searchEdge(a, b);

        if(foundEdge == null)
            return null;

        return foundEdge.getLabel();

    }

    public Collection<V> getNeighbours(V a) {
        Collection<V> neighbours = new ArrayList<V>();
        if(!containsNode(a)) return null;
        
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);

        for (AdjacentNode<V, L> adjacent : adjListA)
            neighbours.add(adjacent.getNode());
        
        return neighbours;
    }

    public Collection<? extends AbstractEdge<V, L>> getEdges() {
        Collection<Edge<V, L>> edges = new ArrayList<>(totalEdges);
        
        for (Map.Entry<V, LinkedList<AdjacentNode<V, L>>> entry : adjacentMappedList.entrySet())
            storeEdges(entry.getKey(), edges);
        
        return edges;
    }
    
    /**
     * It adds an edge to this graph with the specified characteristics assuming that it is safe to add it without encountering any
     * conflicts with an existing edge or without questioning the existance of the nodes.
     * @param a : source node of the edge to add 
     * @param b : destination node of the edge to add 
     * @param l : label of the edge to add
     */
    private void secureEdgeAdd(V a, V b, L l) {
        AdjacentNode<V, L> edgeA = new AdjacentNode<V, L>(b, l);
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);
        adjListA.add(edgeA);
    }

    /**
     * It removes the specified edge assuming that this graph contains the two nodes that form this edge.
     * @param a : source node of the edge to remove 
     * @param b : destination node of the edge to remove 
     * @return true if the edge was in the graph and was removed successfully, false otherwise
     */
    private boolean safeEdgeRemove(V a, V b) {
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);
        Iterator<AdjacentNode<V, L>> iterator = adjListA.iterator();
        while (iterator.hasNext()) {
            AdjacentNode<V, L> edge = iterator.next();
            if(edge.getNode().equals(b)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * It totally erases a node from this graph along with its adjacency list.
     * @param a : node that will be erased
     */
    private void eraseNode(V a) {
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);
        totalEdges -= adjListA.size();
        adjacentMappedList.remove(a);    
    }

    /**
     * It deletes all the incoming edges of a specified node in a non directed graph without visiting every adjacency list of every node in this graph.
     * <b>Note :</b> This method grants a secure edge removal only for non directed graphs.
     * @param a : node whose incoming edges are to be removed
     */
    private void notDirectedEdgesRemove(V a) {
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);

        for (AdjacentNode<V, L> adjacent : adjListA) {
            AdjacentNode<V, L> inverse = new AdjacentNode<>(a, adjacent.getLabel());
            LinkedList<AdjacentNode<V, L>> adjListX = adjacentMappedList.get(adjacent.getNode());
            adjListX.remove(inverse);
        }

    }

    /**
     * It deletes all the incoming edges of a specified node in this graph by vising every adjacency list of every node in this graph.
     * @param a : node whose incoming edges are to be removed
     */
    private void directedEdgesRemove(V a) {
        for (Map.Entry<V, LinkedList<AdjacentNode<V, L>>> entry : adjacentMappedList.entrySet()) {
            LinkedList<AdjacentNode<V, L>> listNodeX = entry.getValue();
            Iterator<AdjacentNode<V, L>> iterator = listNodeX.iterator();
            
            while (iterator.hasNext()) {
                AdjacentNode<V, L> adjacent = iterator.next();
                if (adjacent.getNode().equals(a)) {
                    iterator.remove();
                    break;
                }
            }
        }    
    }

    /**
     * It searches for an edge with the specified parameters in this graph.
     * @param a : source node of the edge to search
     * @param b : destination node of the edge to search
     * @return an {@link AdjacentNode} object containing the searched edge if it currently exists in this graph, null otherwise
     */
    private AdjacentNode<V, L> searchEdge(V a, V b) {
        LinkedList<AdjacentNode<V, L>> adjListA = adjacentMappedList.get(a);

        for (AdjacentNode<V, L> adjacent : adjListA) {
            if (adjacent.getNode().equals(b))
                return adjacent;
            
        }

        return null;
    }

    /**
     * It stores all the adjacent edges of the specfied node, contained in this graph, to a {@link Collection} of edges.
     * It doesn't delete nor modify the already existing objects in the specified collection.
     * @param a : node from where the adjacent edges are retrieved
     * @param edges : a collection of edges where the retrieved edges are stored.
     */
    private void storeEdges(V a, Collection<Edge<V, L>> edges) {
        Edge<V, L> tempEdge;
        LinkedList<AdjacentNode<V, L>> listNodeA = adjacentMappedList.get(a);

        for (AdjacentNode<V, L> adjacent : listNodeA) { 
            tempEdge = new Edge<>(a, adjacent.getNode(), adjacent.getLabel());
            edges.add(tempEdge);
        }    
    }
}
