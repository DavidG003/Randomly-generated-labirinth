package usage;

import graph.*;
import priorityqueue1.*;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Prim {
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * It calculates the minimum spanning forest of the specified graph using Prims' algorithm.
     * @param <V> node type
     * @param <L> label type, it should extend {@link Number} to be considered a weighted graph
     * @param graph : graph which the minimum spanning forest is calculated
     * @return Collection of edges that represent the min spanning forest.
     * @throws PriorityQueueException if there are inconsistent operations in the internal priority queue.
     */
    public static <V, L extends Number> Collection<? extends AbstractEdge<V, L>> minimumSpanningForest(
            Graph<V, L> graph) throws PriorityQueueException {

        int forestSize = graph.numNodes();
        Comparator<WeightedVertex<V>> weighComparator = new VertexComparator<>();

        Graph<V, L> minSpanForest = new Graph<>(true, true);

        HashMap<V, WeightedVertex<V>> weightsMap = new HashMap<>(forestSize);

        if (forestSize == 0)
            return minSpanForest.getEdges();

        PriorityQueue<WeightedVertex<V>> weightQueue = new PriorityQueue<WeightedVertex<V>>(weighComparator);

        initData(graph.getNodes(), weightQueue, weightsMap, minSpanForest);

        while (!weightQueue.empty()) {
            WeightedVertex<V> referenceVertex = weightQueue.top();
            V lightestVertex = referenceVertex.getVertex();
            weightQueue.pop();

            addNewEdge(minSpanForest, referenceVertex);

            for (V neighbour : graph.getNeighbours(lightestVertex)) {
                WeightedVertex<V> foundVertex = weightsMap.get(neighbour);
                L graphWeight = graph.getLabel(lightestVertex, neighbour);

                adjustQueue(weightQueue, lightestVertex, foundVertex, graphWeight);
            }
        }
        return minSpanForest.getEdges();

    }

    public static void main(String[] args) {
        Graph<String, Float> distanceGraph = new Graph<>(false, true);
        loadGraph(args[0], distanceGraph);
        System.err.println("Calculating minimum spanning forset of a graph with" + distanceGraph.numNodes()
                + " nodes and " + distanceGraph.numEdges() + " edges");
        try {

            @SuppressWarnings("unchecked")
            Collection<Edge<String, Float>> minForest = (Collection<Edge<String, Float>>) minimumSpanningForest(distanceGraph);
            System.out.println("Minimum spanning forest calculated. Now printing edges...");
            printGraph(minForest);

        } catch (Exception e) {
            System.err.println("Unable to calculare minimum spanning forest of the given graph");
            return;
        }

    }

    /**
     * It prints all the edges in the given collection and some extra data about the collection.
     * @param minForest : a collection of edges with String nodes and Float labels 
     */
    private static void printGraph(Collection<Edge<String, Float>> minForest) {

        Set<String> uniqueNodes = new HashSet<>(minForest.size());
        double tot = 0;

        for (Edge<String, Float> edg : minForest) {
            System.out.println(edg.getStart() + "," + edg.getEnd() + "," + edg.getLabel());
            uniqueNodes.add(edg.getStart());
            uniqueNodes.add(edg.getEnd());
            tot += edg.getLabel();
        }

        System.err.println("\nNumber of unique nodes in the calculated forest: " + uniqueNodes.size());
        System.err.println("Number of edges in the calculated forest: " + minForest.size());
        System.err.println("Forest total weigth: " + tot / 1000 + " kilometers\n");

    }


    /**
     * It reads a csv file to store the first and second field as nodes and the third field as a labbeled edge between the first two fields.  
     * @param filepath : path of the csv file to read in String format. The csv file must have a sprcific format which is: "String, String, Float \n"
     * @param distanceGraph : graph used to store the nodes and edges retrieved from the csv file
     */
    private static void loadGraph(String filepath, Graph<String, Float> distanceGraph) {
        System.out.println("\nLoading data from file...\n");
        Path inputFilePath = Paths.get(filepath);
        String place1, place2;

        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                place1 = lineElements[0];
                place2 = lineElements[1];
                distanceGraph.addNode(place1);
                distanceGraph.addNode(place2);
                distanceGraph.addEdge(place1, place2, Float.parseFloat(lineElements[2]));
            }
        } catch (Exception e) {
            System.err.println("\nData was not loaded\n");
            return;
        }
        System.out.println("\nData loaded\n");
    }

    /**
     * It initializes a map and a priority queue with new WeightedVertex objects retrieved from the given nodes.
     * It also sets the weight of the first node in the queue equal to zero.
     * @param <V> vertex type.
     * @param nodes : Collection of nodes.
     * @param weightQueue : priority queue that will be initialized
     * @param weightsMap : hash map that will be initialized
     * @param graph : graph where all the elements in {@code nodes} will be stored. 
     * @throws PriorityQueueException if there are no nodes to push in the queue.
     */
    private static <V, L> void initData(Collection<V> nodes, PriorityQueue<WeightedVertex<V>> weightQueue,
            HashMap<V, WeightedVertex<V>> weightsMap, Graph<V,L> graph) throws PriorityQueueException {
        for (V node : nodes) {
            WeightedVertex<V> vertex = new WeightedVertex<V>(node);
            weightQueue.push(vertex);
            weightsMap.put(node, vertex);
            graph.addNode(node);
        }

        weightQueue.top().setWeight(0);
    }

    /**
     * If the new Weigth is smaller than the previous one then it modifies the attributes of a {@link WeightedVertex} that is in the specified priority queue and then it fixes the priority order.
     * @param <V> vertex type
     * @param <L> label type
     * @param weightQueue : queue that should be fixed.
     * @param parentVertex : the vertex from which {@code foundVertex} was found.
     * @param foundVertex : the vertex that has its old weigth confronted with {@code newWeigth}
     * @param newWeight : the new weight to confront with the old one
     */
    private static <V, L extends Number> void adjustQueue(PriorityQueue<WeightedVertex<V>> weightQueue,
            V parentVertex, WeightedVertex<V> foundVertex, L newWeight) {
        if (weightQueue.contains(foundVertex) && newWeight.doubleValue() < foundVertex.getWeight().doubleValue()) {
            foundVertex.setWeight(newWeight);
            foundVertex.setParent(parentVertex);
            weightQueue.increasePriority(foundVertex);
        }
    }

    /**
     * It adds a new {@link Edge} in the specified collection if it's possible to do such operation.
     * @param <V> vertex type
     * @param <L> label type
     * @param dest : graph where the new edge will be added
     * @param referenceVertex : vertex used to create a new edge. Its vertex attribute will be the source vertex;
     *  the parent attribute will be the destination vertex and the weight attribute will be the label of the new edge.
     */
    @SuppressWarnings("unchecked")
    private static <V, L> void addNewEdge(Graph<V, L> dest, WeightedVertex<V> referenceVertex) {  
        if (referenceVertex.getParent() != null) {
            dest.addEdge(referenceVertex.getVertex(), referenceVertex.getParent(), (L) referenceVertex.getWeight());
        }
    }
}
