package priorityqueue1;

/**
* Representation of an abstract queue.  
* @author Georgiev David
* @param <E>: generic type of the abstract queue elements
*/

public interface AbstractQueue<E> {
  
    /**
    * @return true if this queue is empty, false otherwise
    */
    public boolean empty();

    /**
    * It inserts (pushes) the specified element into this queue respecting a given priority protocol. It fails if the element already exists.
    * @param e : the element to be pushed 
    * @return true if the element was pushed correctly, false if the element already existed in the queue
    */
    public boolean push(E e);

    
    /**
    * It checks if this queue contains the specified element
    * @param e : the element to be searcehd in this abstract queue 
    * @return true if the element was found, false otherwise
    */
    public boolean contains(E e);

    /**
    * It gets the element on top of this queue without removing it
    * @return the element on the top of the queue if the queue is not empty
    * @throws Exception if invoked on empty queue
    */
    public E top();

    /**
    * It removes the element on top of this queue if the queue is not empty
    * @throws Exception if invoked on empty queue
    */
    public void pop();

    /**
    * It removes an element of this queue if it exists in it.
    * @param e : the element to be removed from this abstract queue 
    * @return true if the element was removed successfully, false if it didn't exist in the queue
    */
    public boolean remove(E e); 

  };
  