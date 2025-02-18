package priorityqueue1;

import java.util.Comparator;
import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * It specifies a test suite for the Priority Queue library
 * 
 * @author David Georgiev
 */
public class PriorityQueueTests {
  PriorityQueue<Integer> priorityQueue;
  ArrayList<Integer> expectedHeap;

  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }

  @Before
  public void createPriorityQueue() throws PriorityQueueException {
    expectedHeap = new ArrayList<Integer>();
    priorityQueue = new PriorityQueue<Integer>(new IntegerComparator());
  }

  @Test
  public void testIsEmptyZeroEl() {
    assertTrue(priorityQueue.empty());
  }

  @Test
  public void testIsEmptyOneEl() throws Exception {
    priorityQueue.push(12);
    assertFalse(priorityQueue.empty());
  }

  @Test
  public void testPushOneEl() throws Exception {
    Integer el1 = -12;
    priorityQueue.push(el1);
    expectedHeap.add(el1);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testPushManyDifferentEl() throws Exception {
    Integer elems[] = { 10, 2, -1 };
    int size = 3;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    expectedHeap.add(-1);
    expectedHeap.add(10);
    expectedHeap.add(2);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testPushManyEqualEl() throws Exception {
    Integer elems[] = { 10, 10, -1, 10 };
    int size = 4;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    expectedHeap.add(-1);
    expectedHeap.add(10);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testContainsZeroElem() throws Exception {
    Integer toSearch = 1;
    assertFalse((priorityQueue.contains(toSearch)));
  }

  @Test
  public void testContainsWithoutElem() throws Exception {
    Integer elems[] = { 1, 2, 3, 4 };
    int size = 4;
    Integer toSearch = 5;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    assertFalse((priorityQueue.contains(toSearch)));
  }

  @Test
  public void testContainsWithElem() throws Exception {
    Integer elems[] = { 4, 3, 2, 1 };
    int size = 4;
    Integer toSearch = 3;
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    assertTrue((priorityQueue.contains(toSearch)));
  }

  @Test
  public void testTopSingleElem() throws Exception {
    Integer elem = 100;

    priorityQueue.push(elem);

    Integer min = priorityQueue.top();

    assertTrue(min == elem && !priorityQueue.empty());
  }

  @Test
  public void testTopBeforeMinPush() throws Exception {
    Integer elems[] = { 1, 2, 3, 4, -1 };
    Integer pastMin = 1;
    int size = 4;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    Integer min = priorityQueue.top();

    priorityQueue.push(elems[4]);

    assertTrue(min == pastMin);
  }

  @Test
  public void testTopAfterMinPush() throws Exception {
    Integer elems[] = { 1, 2, 3, 4, -1 };
    Integer currentMin = -1;
    int size = 5;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    Integer min = priorityQueue.top();

    assertTrue(min == currentMin);
  }

  @Test
  public void testPopSingleElem() throws Exception {
    Integer elem = 1;

    priorityQueue.push(elem);

    priorityQueue.pop();

    assertTrue(priorityQueue.empty());
  }

  @Test
  public void testPopWithoutSwaps() throws Exception {
    Integer elems[] = { 1, 3, 2 };
    int size = 3;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    expectedHeap.add(2);
    expectedHeap.add(3);

    priorityQueue.pop();

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testPopWithSwaps() throws Exception {
    Integer elems[] = { 9, 6, 4, 3, 1 };
    Integer expected[] = { 3, 4, 6, 9 };

    int size = 4;

    priorityQueue.push(elems[0]);
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i + 1]);
      expectedHeap.add(expected[i]);
    }

    priorityQueue.pop();

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  public void testPopMultipleElems() throws Exception {
    Integer elems[] = { 10, -20, 30, -40, 50 };
    int size = 5;
    int numPops = 3;

    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i]);
    }

    expectedHeap.add(30);
    expectedHeap.add(50);

    for (int i = 0; i < numPops; i++)
      priorityQueue.pop();

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testRemoveMinElem() throws Exception {
    Integer elems[] = { 8, -1, 3, 2 };
    Integer expected[] = { 2, 8, 3 };

    int size = 3;
    Integer toRemove = -1;

    priorityQueue.push(elems[0]);
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i + 1]);
      expectedHeap.add(expected[i]);
    }

    priorityQueue.remove(toRemove);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testRemoveLastElem() throws Exception {
    Integer elems[] = { 8, -1, 3, 2, 7, 5, 4 };
    Integer expected[] = { -1, 2, 3, 8, 7, 5 };

    int size = 6;
    Integer toRemove = 4;

    priorityQueue.push(elems[0]);
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i + 1]);
      expectedHeap.add(expected[i]);
    }

    priorityQueue.remove(toRemove);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testRemoveElemWithParentGreaterThanLast() throws Exception {
    Integer elems[] = { 10, 20, 30, 25, 28, 40, 35 };
    Integer expected[] = { 10, 25, 30, 35, 28, 40 };

    int size = 6;
    Integer toRemove = 20;

    priorityQueue.push(elems[0]);
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i + 1]);
      expectedHeap.add(expected[i]);
    }

    priorityQueue.remove(toRemove);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

  @Test
  public void testRemoveElemWithChildLesserThanLast() throws Exception {
    Integer elems[] = { 8, -1, 3, 2, 7, 5, 4 };
    Integer expected[] = { -1, 2, 3, 8, 7, 5 };
    int size = 6;
    Integer last = 4;

    priorityQueue.push(elems[0]);
    for (int i = 0; i < size; i++) {
      priorityQueue.push(elems[i + 1]);
      expectedHeap.add(expected[i]);
    }

    priorityQueue.remove(last);

    assertTrue(expectedHeap.equals(priorityQueue.getHeap()));
  }

}