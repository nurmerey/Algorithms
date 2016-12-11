import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
 // construct an empty deque 
   private Node first = null;
   private Node last = null;
   
   private class Node
   {
    Item item;
    Node next;
   }
   
   public Deque(){
    
   }
   
// is the deque empty?
   public boolean isEmpty(){
       return first == null;
   }
   
// return the number of items on the deque
   public int size(){
       return 0;
   }
   
// add the item to the front
   public void addFirst(Item item){
       if (item == null) throw new NullPointerException();
       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       if (oldfirst == null) last = first;
   }
   
// add the item to the end
   public void addLast(Item item){
       if (item == null) throw new NullPointerException();
       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.next = null;
       if (isEmpty()) first = last;
       else oldlast.next = last;
   }
   
// remove and return the item from the front
   public Item removeFirst(){
       Item item = first.item;
       first = first.next;
       if (isEmpty()) last = null;
       return item;
   }
   
// remove and return the item from the end
public Item removeLast(){
    Item lastItem = last.item;
    Node previous = null;
    Node current = first;
       
    while (current.next != null) {
        if (current != first) previous = current;
        current = current.next;
    }
       previous.next = null;
       last = previous;
       return lastItem;
   }
   
// return an iterator over items in order from front to end
   public Iterator<Item> iterator(){
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item>{

    private Node current = first;
    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Item next() {
        if (current.next == null) throw new NoSuchElementException();
        Item item = current.item;
        current = current.next;
        return item;   
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
       
   }
// unit testing
   public static void main(String[] args){
       System.out.println("STARTING");
       Deque<String> jo = new Deque<String>();
       jo.addFirst("1");
       jo.addFirst("2");
       jo.addFirst("3");
       jo.addFirst("4");
       
//       jo.removeFirst();
//       jo.addLast("A");
//       jo.addLast("B");
//       jo.addLast("C");
       jo.removeLast();
       System.out.println("first "+ jo.first.item);
       System.out.println("last "+ jo.last.item);
       System.out.println("---------");
   }
}