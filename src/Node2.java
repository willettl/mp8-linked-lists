/**
 * Simple Nodes that are doubly linked 
 *
 * @author Samuel A. Rebelsky
 * @author Lucas Willett
 */
public class Node2<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** 
   * The previous node.
   */
  Node2<T> prev;

  /**
   * The stored value.
   */
  T value;

  /**
   * The next node.
   */
  Node2<T> next;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /** 
   * Create a new node.
   */
  public Node2(Node2<T> prev, T value, Node2<T> next) {
    this.prev = prev;
    this.value = value;
    this.next = next;
  } // Node2(Node2<T>, T, Node2<T>)

  /**
   * Create a new node with no previous link.  (E.g., the front
   * of some kinds of lists.)
   */
  public Node2(T value, Node2<T> next) {
    this(null, value, next);
  } // Node2(T, Node2<T>)

  /**
   * Create a new node with no next link.  (Included primarily
   * for symmetry.)
   */
  public Node2(Node2<T> prev, T value) {
    this(prev, value, null);
  } // Node2(Node2<T>, T)

  /**
   * Create a new node with no links.
   */
   public Node2(T value) {
     this(null, value, null);
   } // Node2(T)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Insert a new value after this node.  Returns the new node.
   */
  Node2<T> insertAfter(T value) {
    Node2<T> tmp = new Node2<T>(this, value, this.next);
    this.next.prev = tmp;
    this.next = tmp;
    return tmp;
  } // insertAfter

  /**
   * Insert a new value before this node.  Returns the new node.
   */
  Node2<T> insertBefore(T value) {
    Node2<T> tmp = new Node2<T>(this.prev, value, this);
    this.prev.next = tmp;
    this.prev = tmp;
    return tmp;
  } // insertBefore

  /**
   * Remove this node.
   */
  void remove() {
    this.prev.next = this.next;
    this.next.prev = this.prev;
    this.prev = null;
    this.next = null;
  } // remove()

} // Node2<T>
