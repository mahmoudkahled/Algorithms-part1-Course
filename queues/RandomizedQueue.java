import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] data;
    private int front = 0 , rear = 0 , len = 1;
    public RandomizedQueue(){
        data = new Object[1];
    }
    public boolean isEmpty(){
        return front == rear;
    }
    public int size(){
        return calculateIndex(rear + data.length - front, data.length);
    }
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(len == data.length){
            resize(2 * data.length);
        }
        data[rear++] = item;
        rear = calculateIndex(rear, data.length);
        ++len;
    }
    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int random_element;
        random_element = StdRandom.uniform(calculateIndex(front, data.length),calculateIndex(rear, data.length));
        Item randomized_element = (Item) data[random_element];
        data[random_element] = data[rear - 1];
        data[rear - 1] = null;
        --rear;
        --len;
        if(len > 0 && len == (data.length/4)){
            resize(data.length / 2);
        }
        return randomized_element;
    }
    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int random_element;
        random_element = StdRandom.uniform(calculateIndex(front, data.length),calculateIndex(rear, data.length));
        return (Item) data[random_element];
    }
    private int calculateIndex(int index , int size){
        if(index >= size){
            return index - size;
        }
        else{
            return index;
        }
    }
    private void resize(int capacity){
        Object[] copy = new Object[capacity];
        for(int i = 0; i < len; i++){
            copy[i] = data[i];
        }
        data = copy;
    }
    public Iterator<Item> iterator(){
        return new MyIterator();
    }
    private class MyIterator implements Iterator<Item>{
        private int index = 0;
        private Object[] copy_data;
        public MyIterator(){
                copy_data = (Item[]) new Object[size()];
                for(int x = 0; x < size() ; x++){
                    copy_data[x] = data[x];
                }
                StdRandom.shuffle(copy_data);
        }
        @Override
        public boolean hasNext(){
            return (index < copy_data.length);
        }
        @Override
        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            Item returned_copy_data = (Item)copy_data[index++];
            return returned_copy_data;
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        Iterator itr = r.iterator();
        r.enqueue(55);
        r.enqueue(55);

        System.out.println(itr.next());
        //System.out.println(itr.next());
    }
}
