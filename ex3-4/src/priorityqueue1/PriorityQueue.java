package priorityqueue1;


import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * Generic priority queue implemented with a min heap structure that is implemented with the use of the ArrayList class.
 *  To determine the priority between the elements a comparator from the class {@link Comparator} is used.
 * This queue doesn't support the insertion of duplicate elements!
 * <p>
 * The heap uses a secondary memory stored in a HashMap structure
 * to ammortize the time complexity of {@code contains} and {@code remove}.
 * The heap-like structure permits to have a time complexity of O(1) for  {@code empty}, {@code top} and {@code cointains} 
 * while limiting to a O(log N) time complexity the {@code push}, {@code pop} and {@code remove} operations.
 * 
 * @author David Georgiev
 * @see ArrayList
 * @see HashMap
 * 
 */
public class PriorityQueue<E> implements AbstractQueue<E>{
    
    private ArrayList<E> minHeap = null;
    private Comparator<E> comparator = null;
    private HashMap<E, Integer> indexMap = null;

   /**
   * Class constructor that creates an empty priority queue
   * It accepts as input a comparator implementing the 
   * precedence relation between this queue's elements.
   * It constructs an initial queue with a capacity of ten.
   * @param comparator : a comparator implementing the precedence relation between this queue elements.
   * @throws PriorityQueueException if the parameter is null
   * @see Comparator
   */
    public PriorityQueue(Comparator<E> comparator){
        
        this.minHeap = new ArrayList<E>(10);
        this.indexMap = new HashMap<E, Integer>(10);
        this.comparator = comparator;
    }

    /**
     * @return a copy of the heap that was used to store the elements 
     */
    public ArrayList<E> getHeap() {
        ArrayList<E> copy = new ArrayList<>(minHeap);
        return copy;
    }

    public boolean empty(){
        return (this.minHeap).isEmpty();
    }


    public E top(){
        if(minHeap.size() == 0) 
            throw new PriorityQueueException("PriorityQueue top(): cannot visualize the top element of an empty queue");

        return minHeap.get(0);
    }

    
    public boolean contains(E e){
        return (indexMap.containsKey(e));
     }
 

    public boolean push(E e){                
        if(contains(e)) 
            return false;

        int i = minHeap.size();
        this.minHeap.add(e);
        indexMap.put(e, i);

        heapifyUp(i);

        return true;
    }

    
    public void pop(){
        if(minHeap.size() == 0) 
            throw new PriorityQueueException("PriorityQueue pop(): cannot extract the top element of an empty queue");

        if(minHeap.size() == 1){
            indexMap.remove(minHeap.get(0));
            minHeap.remove(0);
            return;
        }

        overwriteWithLast(0);

        heapify(0);
    }

    public boolean remove(E e){
        if(!contains(e)) 
            return false;

        Integer removedElemIndx = indexMap.get(e);

        if(removedElemIndx == minHeap.size() -1){
            indexMap.remove(minHeap.get(removedElemIndx));
            minHeap.remove(minHeap.size() - 1);
            return true;
        }

        overwriteWithLast(removedElemIndx);

        int parent = findParent(removedElemIndx);

        if((this.comparator).compare(minHeap.get(removedElemIndx), minHeap.get(parent)) < 0){
            elemSwap(removedElemIndx, parent);
            heapifyUp(parent);
        }else{
            heapify(removedElemIndx);
        }

        return true;
    }

    /**
     * @return parent position of the element in the specified position of this array
     * @param i : index of the element of which the parent is searched for
     */
    private int findParent(int i){
        return (i-1) / 2;
    }
  
    
    /**
     * @return left child position of the element in the specified position of this array
     * @param i : index of the element of which the left child is searched for
     */
    private int findLeft(int i){
        if(2*i+1 <  this.minHeap.size())
          return 2*i+1;
  
        return i;
    }
    
    
    /**
     * @return right child position of the element in the specified position of this array
     * @param i : index of the element of which the right child is searched for
     */
    private int findRight(int i){
        if( (2*i + 2) < this.minHeap.size())
          return (2*i + 2);
  
        return i;
    } 
        
    /**
     * It swaps two elements with the use of their indexes in the array. The order in which a couple of indexes is gives is not important.
     * @param index1 : index of element to be swapped with another
     * @param index2 : index of the second element that will be swapped with the first one.
     */
    private void elemSwap(int index1 , int index2){
        E temp = minHeap.get(index1);
        
        HashSwap(index1, index2);
        
        minHeap.set(index1, minHeap.get(index2));
        minHeap.set(index2, temp);
    }

    /**
     * It maintains the priority order after a destructive operation in the priority queue.
     * @param i : index from where the priority queue shoud begin getting fixed
    */
    private void heapify(int i){
  
        int m = minOfThree(i, findLeft(i),findRight(i));
  
        if(m != i){
          elemSwap(m, i);
          heapify(m);
        }
    }
    
    /**
     * It increases the priority of the given element if this exists in the priority queue and if its priority has been changed to a different value that might precede the ancestors of this element.
     * The priority queue is modified only in two cases: the previous priority value of this element has been mofied to another one that precedes the priority of one or many of its ancestors or if the priority of the parent of the given element has been modified to one that is greater that the given elements' priority.
     * @param e : element that might have his priority increased
     */
    public void increasePriority(E e){
        if(this.contains(e)){
            int poz = this.indexMap.get(e);
            heapifyUp(poz);
        }
    }

    /**
     * It reduces the index of the element in the position i by swapping it with it's parent until the parent is smaller.
     * @param i : index of the element to move upwards in the heap 
    */
    private void heapifyUp(int i){
        int parent = findParent(i);
        while (i > 0 && (this.comparator).compare(minHeap.get(i), minHeap.get(parent)) < 0){
            elemSwap(i, parent);
            i = parent;
            parent = findParent(i);
        }
    }
      
    /**
     * It determines which is the minimum between three elements. The order in which the indexes are given doesn't change the return value of the method
     * @param el1 : index of the first element to be confronted
     * @param el2 : index of the second element to be confronted
     * @param el3 : index of the third element to be confronted
     * @return the index of the minimum element between the given three elements
     * 
     */
    private int minOfThree(int el1, int el2, int el3){
        int min = el1;

        if(el1 != el2){
            if((this.comparator).compare(minHeap.get(min), minHeap.get(el2)) > 0)
                min = el2;
        }
        
        if(el2 == el3 || el1 == el3) return min;
  
        if((this.comparator).compare(minHeap.get(min), minHeap.get(el3)) > 0)
          min = el3;
        
        return min;
    }

    /**
     * It swaps the index value of two existing keys in the Hashmap of this priority queue. The order in which the parameters are passed is not important
     * @param index1 : first index to swap
     * @param index2 : second index to swap
     */
    private void HashSwap(int index1, int index2){

        indexMap.replace(minHeap.get(index2), index1);
        indexMap.replace(minHeap.get(index1), index2);

    }

    /**
     * It overwrites the element that is found on the specified index of the heap with the last element, then it deletes the element in the last position.
     * @param i : index of the element to overwrite.
     */
    private void overwriteWithLast(int i){

        indexMap.remove(minHeap.get(i));
        minHeap.set(i, minHeap.get(minHeap.size()- 1));
        indexMap.replace( minHeap.get(i), i);
        minHeap.remove(minHeap.size() - 1);

    }

}
