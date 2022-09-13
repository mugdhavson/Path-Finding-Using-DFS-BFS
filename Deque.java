/**
 * @author MugdhaSonawane
 * 
 * @author Instructor
 * 
 * File: Deque.java
 * Class: CSC 345, Spring 2022
 * Instructor: Melanie Lotz
 * Assignment: Project 3
 * 
 * PURPOSE: this represents a deque data structure, 
 * i.e. an array with pointers to the front and the back
 * 
 */

//@SuppressWarnings("unchecked")
public class Deque<Item> {
    private Item[] deque;
    private int front;//points to the front item in the deque
    private int back;//points to the first open position at the back of the deque
    private int size;
    private int init_cap;

    /***
     *constructor: create an empty Deque with initial
     *capacity of 10
     */
    public Deque() {
	this.deque = (Item[]) new Object[10];
	this.size = 0;
	this.front = 0;
	this.back = 0;
	this.init_cap = 10;
    }

    /***
     *constructor: create an empty Deque with initial
     *capacity of n
     */
    public Deque(int n) {
	this.deque = (Item[]) new Object[n];
	this.size = 0;
	this.front = 0;
	this.back = 0;
	this.init_cap = n;
    }
    
    /***
     *add an item to the front of the Deque
     *double the array capacity if Deque is full
     */
    public void addToFront(Item item) {
	if(this.isEmpty()) {
	    deque[front] = item;
	    changeIndex(true, false);
	    size++;
	    return;
	} else if(this.size == deque.length)
	    this.resize(2*deque.length);
	changeIndex(false, true);
	deque[front] = item;
	size++;
    }

    /***
     *add an item to the back of the Deque;
     *double the array capacity if the Deque is full
     ***/
    public void addToBack(Item item) {
	if(this.size() == deque.length)
	    this.resize(2*deque.length);
	deque[back] = item;
	size++;
	changeIndex(true, false);
    }

    /***
     *remove and return the front item from the Deque;
     *throw EmptyDequeException if the Deque is empty;
     *reduce the array capacity by half if the size 
     *of the Deque size falls below floor(N/4) where
     *N is the array capacity, but do not resize it 
     *to be below the default capacity, which is 10
     ***/
    public Item getFirst() throws EmptyDequeException {
	if(size == 0)
	    throw new EmptyDequeException();
	Item ret = deque[front];
	size--;
	deque[front] = null;
	changeIndex(true, true);
	int fourth = deque.length/4;
	if(size < fourth && deque.length/2 >= init_cap)
	    resize(deque.length/2);
	return ret;
    }

     /***
     *remove and return the back item from the Deque;
     *throw EmptyDequeException if the Deque is empty;
     *reduce the array capacity by half if the size 
     *of the Deque size falls below floor(N/4) where
     *N is the array capacity, but do not resize it 
     *to be below the default capacity, which is 10
     ***/
    public Item getLast() throws EmptyDequeException {
	if(size == 0)
	    throw new EmptyDequeException();
	changeIndex(false, false);
	Item ret = deque[back];
	size--;
	deque[back] = null;
	int fourth = deque.length/4;
	if(size < fourth && deque.length/2 >= init_cap)
	    resize(deque.length/2);
	return ret;
    }
    
    /***
     *return true if the Deque is empty;
     *return false if the Deque is not empty
     ***/
    public boolean isEmpty() {
	return size == 0;
    }

    /***
     *return the size of the Deque (i.e. the 
     *number of elements currently in the Deque)
     ***/
    public int size() {
	return this.size;
    }

    /***
     *return but do not remove the front item in the Deque;
     *throw an EmptyDequeException if the Deque is empty
     */
    public Item peekFirst() throws EmptyDequeException {
	if(size == 0)
	    throw new EmptyDequeException();
	return deque[front];
    }

    /***
     *return but do not remove the back item in the Deque;
     *throw an EmptyDequeException if the Deque is empty
     */
    public Item peekLast() throws EmptyDequeException {
	if(size == 0)
	    throw new EmptyDequeException();
	int b = (back - 1) % deque.length;
	if(b < 0)
	    b += deque.length;
	return deque[b];
    }
    
    /***
     *return the underlying array
     ***/
    public Item[] getArray() {
	return deque;
    }

    public void printDeque() {
	for(int i = front; i != back; i = (i+1)%deque.length)
	    System.out.println(deque[i]);
    }
    

    /***
     *resize the array to be of size max
     ***/
    private void resize(int max) {
	Item[] newArray = (Item[]) new Object[max];
	for(int i = 0; i < size; i++) {
	    newArray[i] = deque[front];
	    changeIndex(true, true);
	}
	deque = newArray;
	this.front = 0;
	this.back = size;
    }

    /***
     *increment or decrement front or back index
     ***/
    private void changeIndex(boolean inc, boolean isFront) {
	if(inc && isFront)  {
	    front = (front + 1) % deque.length;
	}
	else if(!inc && isFront) {
	    front = (front - 1) % deque.length;
	    if(front < 0)
		front += deque.length;
	} else if(inc && !isFront) 
	    back = (back + 1) % deque.length;
	else {
	    back = (back - 1) % deque.length;
	    if(back < 0)
		back += deque.length;
	}
    }
    
    
    /**
     * This function removes all of the elements from the 
     * deque.
     * 
     * Need it for ending bfs once a word's path has been 
     * found.
     * 
     * @throws EmptyDequeException
     */
    public void makeEmpty() {
    	int control = this.size;
    	for(int i = 0; i < control; i++) {
    		try {
				this.getFirst();
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * This function checks to see whether a Loc with 'row' 
     * and 'col' are present in the deque
     * 
     * RETURN VALUE: true, if the specific Loc is present.
     * False, otherwise.
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean inDeque(int row, int col) {
    	// check if loc is in deque
    	for(int i = front; i != back; i = (i+1)%deque.length) {
    		if (((Loc)deque[i]).row == row && ((Loc)deque[i]).col == col) {
    			return true;
    		}
    	}
    	return false;
    }
}
