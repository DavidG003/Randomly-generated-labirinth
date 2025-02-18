package graph;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * It specifies a test suite for the Graph library
 * 
 * @author David Georgiev
 */

public class GraphTest {
    private Graph<String, Integer> directedLabelledGraph;
    private Graph<String, Integer> undirectedLabelledGraph;
    private Graph<String, Integer> directedUnlabelledGraph;
    private Graph<String, Integer> undirectedUnlabelledGraph;

    @Before
    public void setUp() {
        directedLabelledGraph = new Graph<>(true, true);
        undirectedLabelledGraph = new Graph<>(false, true);
        directedUnlabelledGraph = new Graph<>(true, false);
        undirectedUnlabelledGraph = new Graph<>(false, false);
    }

    @Test
    public void testIsDirectedOnDirected() {
        assertTrue(directedLabelledGraph.isDirected());
        assertTrue(directedUnlabelledGraph.isDirected());
    }
    
    @Test
    public void testIsDirectedOnUndirected() {
        assertFalse(undirectedLabelledGraph.isDirected());
        assertFalse(undirectedUnlabelledGraph.isDirected());
    }

    @Test
    public void testIsLabelledOnLabelled() {
        assertTrue(directedLabelledGraph.isLabelled());
        assertTrue(undirectedLabelledGraph.isLabelled());
    }

    @Test
    public void testIsLabelledOnUnlabelled() {
        assertFalse(directedUnlabelledGraph.isLabelled());
        assertFalse(undirectedUnlabelledGraph.isLabelled());
    }

    
@Test
public void testContainsNodeSuccess() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("C");

    assertTrue(directedLabelledGraph.containsNode("C"));
}

@Test
public void testContainsNodeFail() {
    directedLabelledGraph.addNode("C");
    directedLabelledGraph.addNode("A");

    assertFalse(directedLabelledGraph.containsNode("B"));
}

@Test
public void testContainsEdgeSuccess() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    assertTrue(directedLabelledGraph.containsEdge("A", "B"));
}

@Test
public void testContainsEdgeFail() {
    directedLabelledGraph.addNode("D");
    directedLabelledGraph.addNode("F");
    directedLabelledGraph.addEdge("D", "F", 1);
    assertFalse(directedLabelledGraph.containsEdge("A", "B"));
}


@Test
public void testAddNodeOneNode() {
    assertTrue(directedLabelledGraph.addNode("A"));
}

@Test
public void testAddNodeMoreEqualNodes() {
    assertTrue(directedLabelledGraph.addNode("A"));
    assertTrue(directedLabelledGraph.addNode("B"));
    assertFalse(directedLabelledGraph.addNode("A"));

}

@Test
public void testAddEdgeMultipleTimes() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    assertTrue(directedLabelledGraph.addEdge("A", "B", 1));
    assertFalse(directedLabelledGraph.addEdge("A", "B", 1));
}

@Test
public void testAddEdgeUnlabelledWithoutNodes() {
    assertFalse(undirectedUnlabelledGraph.addEdge("A", "B", null));
}

@Test
public void testAddEdgeUnlabelledWithNodes() {
    undirectedUnlabelledGraph.addNode("A");
    undirectedUnlabelledGraph.addNode("B");
    assertTrue(undirectedUnlabelledGraph.addEdge("A", "B", null));
}


@Test
public void testRemoveEdgeFromEmptyGraph() {
    assertFalse(directedLabelledGraph.removeEdge("A", "C"));
}

@Test
public void testRemoveEdgeMultipleTimes() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    assertTrue(directedLabelledGraph.removeEdge("A", "B"));
    assertFalse(directedLabelledGraph.removeEdge("A", "B"));
}

@Test
public void testRemoveEdgeWithoutExistingNodes() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    assertFalse(directedLabelledGraph.removeEdge("A", "C"));
}


@Test
public void testNumNodes() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    assertEquals(2, directedLabelledGraph.numNodes());
}

@Test
public void testNumEdgesEmpty() {
    assertEquals(0, directedLabelledGraph.numEdges());
}

@Test
public void testNumEdgesWithFailures() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    directedLabelledGraph.addEdge("A", "C", 1);
    directedLabelledGraph.addEdge("A", "B", 2);

    assertEquals(1, directedLabelledGraph.numEdges());
}

@Test
public void testNumEdgesUndirected() {
    undirectedLabelledGraph.addNode("A");
    undirectedLabelledGraph.addNode("B");
    undirectedLabelledGraph.addEdge("A", "B", 1);

    assertEquals(1, undirectedLabelledGraph.numEdges());
}


@Test
public void testRemoveNodeEmptyGraph() {
    assertFalse(directedLabelledGraph.removeNode("C"));
}

@Test
public void testRemoveNodeSuccessful() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    directedLabelledGraph.addEdge("B", "A", 2);

    assertTrue(directedLabelledGraph.removeNode("A"));
    assertFalse(directedLabelledGraph.containsEdge("A", "B"));
    assertFalse(directedLabelledGraph.containsEdge("B", "A"));

}

@Test
public void testRemoveNodeFailed() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);

    assertFalse(directedLabelledGraph.removeNode("C"));
    assertTrue(directedLabelledGraph.containsNode("A"));
    assertTrue(directedLabelledGraph.containsNode("B"));
    assertTrue(directedLabelledGraph.containsEdge("A", "B"));

}

@Test
public void testRemoveNodeUndirected() {
    undirectedLabelledGraph.addNode("A");
    undirectedLabelledGraph.addNode("B");
    undirectedLabelledGraph.addEdge("A", "B", 1);

    assertTrue(undirectedLabelledGraph.removeNode("A"));
    assertFalse(undirectedLabelledGraph.containsEdge("A", "B"));
    assertFalse(undirectedLabelledGraph.containsEdge("B", "A"));

}

@Test
public void testGetNodesEmpty() {

    Collection<String> expected = new HashSet<>();
    assertEquals(expected, new HashSet<>(directedLabelledGraph.getNodes()));
}

@Test
public void testGetNodesMultipleNodes() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addNode("C");

    Collection<String> expected = new HashSet<>();
    expected.add("C");
    expected.add("B");
    expected.add("A");
    assertEquals(expected, new HashSet<>(directedLabelledGraph.getNodes()));
}

@Test
public void testGetLabelExistingEdge() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addEdge("A", "B", 1);
    assertEquals(Integer.valueOf(1), directedLabelledGraph.getLabel("A", "B"));
}

@Test
public void testGetLabelNonExistingEdge() {
    undirectedLabelledGraph.addNode("A");
    undirectedLabelledGraph.addNode("B");
    undirectedLabelledGraph.addEdge("A", "B", 1);
    assertNull(undirectedLabelledGraph.getLabel("C", "B"));
}

@Test
public void testGetLabelUnlabelledGraph() {
    directedUnlabelledGraph.addNode("A");
    directedUnlabelledGraph.addNode("B");
    directedUnlabelledGraph.addEdge("A", "B", 1);
    assertNull(directedUnlabelledGraph.getLabel("A", "B"));
}


@Test
public void testGetNeighboursEmptyGraph() {
  
    Collection<String> expected = new ArrayList<>();

    assertNull(directedLabelledGraph.getNeighbours("A"));
}

@Test
public void testGetNeighboursNoNeighbours() {
    undirectedLabelledGraph.addNode("A");
    undirectedLabelledGraph.addNode("B");
    undirectedLabelledGraph.addNode("C");
    undirectedLabelledGraph.addEdge("A", "B", 1);

    Collection<String> expected = new ArrayList<>();

    assertEquals(expected, undirectedLabelledGraph.getNeighbours("C"));
}


@Test
public void testGetNeighboursMultipleNeighbours() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addNode("C");
    directedLabelledGraph.addEdge("A", "B", 1);
    directedLabelledGraph.addEdge("A", "C", 2);
    Collection<String> expected = new ArrayList<>();
    expected.add("B");
    expected.add("C");
    assertEquals(expected, directedLabelledGraph.getNeighbours("A"));
}

@Test
public void testGetEdgesEmptyGraph() {

    Collection<? extends AbstractEdge<String, Integer>> edgesCollection = directedLabelledGraph.getEdges();
    ArrayList<AbstractEdge<String, Integer>> edges = new ArrayList<>(edgesCollection);

    assertEquals(0, edges.size());
}

@Test
public void testGetEdgesNonDirectedGraph() {
    undirectedLabelledGraph.addNode("A");
    undirectedLabelledGraph.addNode("B");
    undirectedLabelledGraph.addEdge("A", "B", 1);

    Collection<? extends AbstractEdge<String, Integer>> edgesCollection = undirectedLabelledGraph.getEdges();
    ArrayList<AbstractEdge<String, Integer>> edges = new ArrayList<>(edgesCollection);
  
    assertTrue(edges.contains(new Edge<>("A", "B", 1)));
    assertTrue(edges.contains(new Edge<>("B", "A", 1)));
    assertEquals(2, edges.size());
}

@Test
public void testGetEdgesDirectedGraph() {
    directedLabelledGraph.addNode("A");
    directedLabelledGraph.addNode("B");
    directedLabelledGraph.addNode("C");
    directedLabelledGraph.addEdge("A", "B", 1);
    directedLabelledGraph.addEdge("A", "C", 2);

    Collection<? extends AbstractEdge<String, Integer>> edgesCollection = directedLabelledGraph.getEdges();
    ArrayList<AbstractEdge<String, Integer>> edges = new ArrayList<>(edgesCollection);
  
    assertTrue(edges.contains(new Edge<>("A", "B", 1)));
    assertTrue(edges.contains(new Edge<>("A", "C", 2)));
    assertEquals(2, edges.size());
}

}