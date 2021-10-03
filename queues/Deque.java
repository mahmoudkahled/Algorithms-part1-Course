import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item>{
    private int size = 0;
    private Node<Item> head = null;
    private Node<Item> tail = null;
    private static class Node<Item>{
        private Item data;
        private Node<Item> prev, next;
        public Node(){}
        public Node(Item data, Node<Item> prev , Node<Item> next){
            this.data = data;
            this.prev = prev;
            this.next = next;
        }@Override
        public String toString(){
            return data.toString();
        }
    }
    public Deque(){}
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){
        return this.size;
    }
    public void addFirst(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            head = tail = new Node<Item>(item,null,null);
        }
        else{
            head.prev = new Node<Item>(item,null,head);
            head = head.prev;
        }
        size++;
    }
    public void addLast(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            head = tail = new Node<Item>(item,null,null);
        }
        else{
            tail.next = new Node<Item>(item,tail,null);
            tail = tail.next;
        }
        size++;
    }
    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item data = head.data;
        head = head.next;
        size--;
        if(isEmpty()){
            tail = null;
        }
        else{
            head.prev = null;
        }
        return data;
    }
    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Item data = tail.data;
        tail = tail.prev;
        size--;
        if(isEmpty()){
            head = null;
        }
        else{
            tail.next = null;
        }
        return data;
    }
    public Iterator<Item> iterator(){
        return new Iterator<Item>(){
            private Node<Item> trav = head;
            @Override
            public boolean hasNext() {
                return trav != null;
            }
            public Item next(){
                if(isEmpty()){
                    throw new NoSuchElementException();
                }
                Item data = trav.data;
                trav = trav.next;
                return data;
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        
    }
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        System.out.println(d.isEmpty());
        d.addFirst("hello");
        d.addLast("sasd");
        d.isEmpty();
        d.removeFirst();
        int size = d.size();
        System.out.println(size);
        String dddd = d.removeFirst();
        System.out.println(dddd);
    }
}