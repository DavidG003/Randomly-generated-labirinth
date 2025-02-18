package usage;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

import graph.*;
import priorityqueue1.*;

//in ex3-4
// javac -d out -sourcepath src src/usage/Labirinth.java src/usage/Labirinth.java 
// oppure in out: javac -d ../out -sourcepath ../src ../src/usage/Labirinth.java ../src/usage/Labirinth.java

//in out
// java usage/Labirinth

/*DA FARE
 * 1) fare labirinti piu grossi, usa generazione randomica da 20x20 a 100x100 dimensione matrice oppure uno scanner per scegliere la dimensione (fatto<)
 * 2) migiora grafica; cerca nuove librerie grafiche di java online
 * 3) determina un inizio e una fine del labirinto (opzionale, basta da alto sx fino a basso dx)
 */

public class Labirinth {
    public static void main(String[] args) {
    
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Insert labirynth size: ");
        int size = input.nextInt();

        Integer[][] matrix = new Integer[size][size];

        Graph<Integer, Float> labr = new Graph<>(false, true);
        ArrayList<? extends AbstractEdge<Integer, Float>> finalLab;
        
        Integer k = 0;
        for(Integer i = 0; i < size; i++){
            for(Integer j = 0; j < size; j++){
                labr.addNode(k);
                matrix[i][j] = k;
                k++;
            }
        }
        k = 0;
        int j;

        for(int i = 0; i < size ; i++){
            for(j = 0; j < size - 1; j++){
                labr.addEdge(matrix[i][j], matrix[i][j+1], (float) Math.random());
            }
            if(i < size - 1){
                for(j = 0; j < size; j++){
                    labr.addEdge(matrix[i][j], matrix[i+1][j], (float) Math.random());
                }
            }
        }
        System.out.println();

        try {
            finalLab = (ArrayList<? extends AbstractEdge<Integer, Float>>) minimumSpanningForest(labr);
            System.out.println("Minimum spanning forest calculated. Now printing labirinth...");
        } catch (Exception e) {
            System.err.println("Unable to calculare minimum spanning forest of the given graph");
            return;
        }

       // stampa labirinto 
       printLabirinth(finalLab, matrix, size);
    }

    private static void printLabirinth(ArrayList<? extends AbstractEdge<Integer, Float>> lab, Integer[][] matrix, int size) {
        Edge<Integer, Float> temp = new Edge<Integer,Float>(null, null, null);
        Edge<Integer, Float> temp2 = new Edge<Integer,Float>(null, null, null);


        JFrame frameObj = new JFrame();    
        frameObj.setLayout(new GridLayout());    
        frameObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid gridPanel = new Grid(size*2 , size*2 , 9);
        frameObj.setSize(1500, 1500);   
 
        frameObj.add(gridPanel);

        frameObj.setVisible(true);
      gridPanel.requestFocusInWindow();

        //coloro fine
        gridPanel.fillCell(size*2, size*2, Color.RED);

        //coloro nodi
        for(int i = 1; i <= size ; i++){
            for(int j = 1; j <= size ; j++){
                    gridPanel.fillCell(i*2, j*2, Color.WHITE);
            }
        }

        //coloro archi
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size - 1; j++){
                temp.setStart(matrix[i][j]);
                temp.setEnd(matrix[i][j+1]);
                temp2.setStart(matrix[i][j+1]);
                temp2.setEnd(matrix[i][j]);
                if(lab.contains(temp) || lab.contains(temp2)){
                   gridPanel.fillCell(((i+1)*2)  , ((j+1) * 2 + 1), Color.WHITE);
                }
            }

            if(i < size - 1){
                for(int j = 0; j < size; j++){
                    temp.setStart(matrix[i][j]);
                    temp.setEnd(matrix[i+1][j]);
                    temp2.setStart(matrix[i+1][j]);
                    temp2.setEnd(matrix[i][j]);
                    if(lab.contains(temp) || lab.contains(temp2)){
                        gridPanel.fillCell(((i+1) * 2 + 1), (j + 1)*2, Color.WHITE);
                    }
                }
            }
        }
    }

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

    @SuppressWarnings("unchecked")
    private static <V, L> void addNewEdge(Graph<V, L> dest, WeightedVertex<V> referenceVertex) {
        if (referenceVertex.getParent() != null) {
            dest.addEdge(referenceVertex.getVertex(), referenceVertex.getParent(), (L) referenceVertex.getWeight());
        }
    }

    private static <V, L extends Number> void adjustQueue(PriorityQueue<WeightedVertex<V>> weightQueue,
            V parentVertex, WeightedVertex<V> foundVertex, L newWeight) {
        if (weightQueue.contains(foundVertex) && newWeight.doubleValue() < foundVertex.getWeight().doubleValue()) {
            foundVertex.setWeight(newWeight);
            foundVertex.setParent(parentVertex);
            weightQueue.increasePriority(foundVertex);
        }
    }

    private static <V, L> void initData(Collection<V> nodes, PriorityQueue<WeightedVertex<V>> weightQueue,
            HashMap<V, WeightedVertex<V>> weightsMap, Graph<V, L> graph) throws PriorityQueueException {
        for (V node : nodes) {
            WeightedVertex<V> vertex = new WeightedVertex<V>(node);
            weightQueue.push(vertex);
            weightsMap.put(node, vertex);
            graph.addNode(node);
        }

        weightQueue.top().setWeight(0);
    }
}

 
/*  stampa semplice senza grafica
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size - 1; j++){
                temp.setStart(matrix[i][j]);
                temp.setEnd(matrix[i][j+1]);
                temp2.setStart(matrix[i][j+1]);
                temp2.setEnd(matrix[i][j]);
                if(lab.contains(temp) || lab.contains(temp2)){
                    System.out.print( "----");
                }else{
                    System.out.print( "    ");
                }
            }
            System.out.println("");
            
            if(i < size - 1){
                for(int j = 0; j < size; j++){
                    temp.setStart(matrix[i][j]);
                    temp.setEnd(matrix[i+1][j]);
                    temp2.setStart(matrix[i+1][j]);
                    temp2.setEnd(matrix[i][j]);
                    if(lab.contains(temp) || lab.contains(temp2)){
                        System.out.print("|   ");
                    }else{
                        System.out.print( "    ");
                    }
                }
                System.out.println();

            }
        }
*/