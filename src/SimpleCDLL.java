import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Simple circular double linked lists with a dummy node
 *
 * @author Samuel A. Rebelsky
 * @author Lucas Willett
 */

public class SimpleCDLL<T> implements SimpleList<T> {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The front of the list
   */
  Node2<T> front;

  /**
   * The dummy node of the list
   */
  Node2<T> dummy;

  /**
   * The number of values in the list.
   */
  int size;

  /**
  * The counter for the number of changes in the list.
  */
  int changes;

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.dummy = new Node2<>(null);
    this.dummy.next = this.dummy;
    this.dummy.prev = this.dummy;
    this.front = new Node2<>(null);
    this.size = 0;
    this.changes = 0;
  } // SimpleDLL

  public Iterator<T> iterator() {
    return listIterator();
  } // iterator()

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      /**
       * The position in the list of the next value to be returned.
       * Included because ListIterators must provide nextIndex and
       * prevIndex.
       */
      int pos = 0;

      /**
      * The counter for the number of changes in the list.
      */
      int changes = SimpleCDLL.this.changes;

      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value..
       */
      Node2<T> prev = SimpleCDLL.this.dummy;
      Node2<T> next = SimpleCDLL.this.front;

      /**
       * The node to be updated by remove or set.  Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      public void add(T val) throws UnsupportedOperationException {
        this.checkValid();
        // Special case: The list is empty
        if (SimpleCDLL.this.size == 0) {
          SimpleCDLL.this.front = new Node2<T>(SimpleCDLL.this.dummy, val, SimpleCDLL.this.dummy);
          SimpleCDLL.this.dummy.next = SimpleCDLL.this.front;
          SimpleCDLL.this.dummy.prev = SimpleCDLL.this.front;
          this.next = SimpleCDLL.this.dummy;
          this.prev = SimpleCDLL.this.front;
        } // empty list
        // Normal case
        else {
          this.prev = this.prev.insertAfter(val);
          SimpleCDLL.this.front = SimpleCDLL.this.dummy.next;
        } // normal case

        // Note that we cannot update
        this.update = null;

        // Increase the size
        ++SimpleCDLL.this.size;

        // Update the position.  (See SimpleArrayList.java for more of
        // an explanation.)
        ++this.pos;

        //Updates the change counter for the Simple CDLL
        ++SimpleCDLL.this.changes;
        //And updates the change counter for this iterator
        ++this.changes;
      } // add(T)

      public boolean hasNext() {
        this.checkValid();
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        this.checkValid();
        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        this.checkValid();
        if (!this.hasNext()) {
         throw new NoSuchElementException();
        } // if
        // Identify the node to update
        this.update = this.next;
        // Advance the cursor
        this.prev = this.next;
        this.next = this.next.next;
        // Note the movement
        ++this.pos;
        // And return the value
        return this.update.value;
      } // next()

      public int nextIndex() {
        this.checkValid();
        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        this.checkValid();
        return this.pos - 1;
      } // prevIndex

      public T previous() throws NoSuchElementException {
        this.checkValid();
        if (!this.hasPrevious())
          throw new NoSuchElementException();
        this.update = this.prev;
        this.next = this.prev;
        this.prev = this.prev.prev;
        --this.pos;
        return this.update.value;
      } // previous()

      public void remove() {
        this.checkValid();
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if

        // Update the cursor
        if (this.next == this.update) {
          this.next = this.update.next;
        } // if
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          --this.pos;
        } // if

        // Update the front
        if (SimpleCDLL.this.front == this.update) {
          SimpleCDLL.this.front = this.update.next;
        } // if

        // Do the real work
        this.update.remove();
        --SimpleCDLL.this.size;

        // Note that no more updates are possible
        this.update = null;

        //Updates the change counter for the Simple CDLL
        ++SimpleCDLL.this.changes;
        //And updates the change counter for this iterator
        ++this.changes;
      } // remove()

      public void set(T val) {
        this.checkValid();
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // Do the real work
        this.update.value = val;
      } // set(T)

      //Checks if the no. of changes when iterator was made is the same as the no. changes now
      //Called at the beginning of every function
      public void checkValid(){
        if(this.changes != SimpleCDLL.this.changes){
          throw new ConcurrentModificationException();
        }
      }//checkValid()
    };
  } // listIterator()

}