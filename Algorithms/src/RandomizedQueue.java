import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
 // construct an empty randomized queue
   private Item[] queue; // items in the queue
   private int currentIndex; // size of the queue
   
   public RandomizedQueue() {
       resize(1);
       currentIndex = 0;
   }
   
   // is the queue empty?
   public boolean isEmpty() {
    return currentIndex == 0;
   }
   
   // return the number of items on the queue
   public int size() {
    return currentIndex;
   }
   
   // add the item
   public void enqueue(Item item) {
       if (item == null) throw new NullPointerException();
       if (currentIndex == queue.length) resize(2*queue.length);
       queue[currentIndex++] = item;
   }
   
   // remove and return a random item
   public Item dequeue() {
       if (isEmpty()) throw new NoSuchElementException();
       int randomInt = StdRandom.uniform(currentIndex);
       Item randomItem = queue[randomInt];
       Item[] newCopy = (Item[]) new Object[this.size()];
       int i = 0;
       for (Item s: this) {
           if (!s.equals(randomItem)) {
               newCopy[i] = s;
               i++;
           }
       }
       queue = newCopy;
       currentIndex--;
    return randomItem;
   }
   
// return (but do not remove) a random item
   public Item sample() {
       if (isEmpty()) throw new NoSuchElementException();
       int randomInt = StdRandom.uniform(1, currentIndex);
       Item randomItem = queue[randomInt];
    return randomItem;
   }
   
// return an independent iterator over items in random order
   public Iterator<Item> iterator() {
    return new RandomIterator();
   }
   
   private class RandomIterator implements Iterator<Item> {
   
    private int i = 0;
    private Item[] copyOfQueue; // copy items in the queue
    public RandomIterator() {
        copyOfQueue = (Item[]) new Object[currentIndex];
        for (int j = 0; j < currentIndex; j++)
            copyOfQueue[j] = queue[j];
        StdRandom.shuffle(copyOfQueue);
    }
    
    @Override
    public boolean hasNext() {
        return i < currentIndex;
    }

    @Override
    public Item next() {
        if (!hasNext()) throw new NoSuchElementException();
        return copyOfQueue[i++];
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
   }
   
   
private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < currentIndex; i++)
        copy[i] = queue[i];
    queue = copy;
   }
// unit testing
   public static void main(String[] args) {
       System.out.println("RandomizedQueue--------");
       RandomizedQueue<String> randQ = new RandomizedQueue<String>();
       randQ.enqueue("AA");
       randQ.enqueue("BB");
       randQ.enqueue("CC");
       randQ.enqueue("DD");
       randQ.enqueue("EE");
       randQ.enqueue("FF");
       randQ.enqueue("GG");
       randQ.enqueue("HH");
       for (String s: randQ) {
           System.out.println("------");
           System.out.println(s);
       }
       
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
//       System.out.println("dequeue "+randQ.dequeue());
       
       for (String s: randQ) {
           System.out.println("------");
           System.out.println(s);
       }
//       System.out.println("sample "+randQ.sample());
       System.out.println(randQ.isEmpty());
       System.out.println(randQ.size());
       
       // 1.dequque - done
       // 2 resize - done
       // 3.randomizer - done
       // 4.subset
   }
}

