public class ArrayDeque<T> {
    public class Node {
        public T[] item;
        private int left;
    private int right;
    private int capacity = 8;


        public Node(T i, LinkedListDeque<T>.Node n, LinkedListDeque<T>.Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    Node head; //sentinel
    Node tail; //sentinel

    // 0 -> 1 -> 2 - > 3  head = 0; tail = 3
    // 0 head = 0 tail = Null
    // head = null tail = null
    // sentinel / dummy head
    // 香肠
    // (0) -> 0 -> 1 -> 2 -> (0)
    // (0) -> (0)
    int size;

    public ArrayDeque() {
        size = 0;
        head = new Node(null, null, null); // (0) : head
        tail = new Node(null, null, null); // (0) : tail

        head.next = tail; // (0 head) -> (0 tail)
        tail.prev = head; // (0 head) <- (0 tail)
    }

    public void addFirst(T item) {
        // (0 head) -> (0 tail)
        // addFirst(1)
        // (0 head) -> (1) -> (0 tail)
        size++;
        Node node = new Node(item, head.next, head);
        head.next.prev = node;
        head.next = node;
        //        (3)
        //       /    \
        //(0 head) -> (1) -> (2) -> (0 tail)

        // (0 head) -> (1) -> (2) -> (0 tail)
        // addFirst(3)
        // (0 head) -> (3) -> (1) -> (2) -> (0 tail)
    }

    public void addLast(T item) {
        // (0 head) <-> (1) <-> (2) <-> (0 tail)
        // addLast(3)
        // (0 head) <-> (1) -> (2) <-> (3) <-> (0 tail)
        size++;
        Node node = new Node(item, tail, tail.prev);
        tail.prev.next = node;
        tail.prev = node;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        //System.out.print
        Node pointer = head.next;
        for (int i = 0; i < size; i++) {
            System.out.print(pointer.item);
            System.out.print(" ");
            pointer = pointer.next;
        }
        return; //optional
    }

    public T removeFirst() {
        // (0 head) <-> (0 tail)
        if (isEmpty()) {
            return null;
        }
        // (0 head) <-> (1) <-> (2) <-> (0 tail)
        Node ret = head.next;
        head.next.next.prev = head;
        head.next = head.next.next;
        //          (1)
        //
        // (0 head) <-> (2) <-> (0 tail)
        size--;
        return ret.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node ret = tail.prev;
        // (0 head) <-> (1) <-> (2) <-> (0 tail)
        tail.prev.prev.next = tail; // (1) -> (0 tail)
        tail.prev = tail.prev.prev; // (1) <- (0 tail)
        // (0 head) <-> (1) <-> (0 tail)
        size--;
        return ret.item;
    }

    public T get(int index) { // iterative
        if (index >= size) {
            return null;
        }
        // (0 head) <-> (0) <-> (1) <-> (2) <-> (3) <-> (0 tail)
        // get(1)
        Node tmp = head; // (0)
        for (int i = 0; i <= index; i++) {
            tmp = tmp.next;
        }
        return tmp.item;
    }

    public T getRecursion(int index) { // method List
        if (index >= size) {
            return null;
        }
        return helper(head.next, index);
    }

    private T helper(Node n, int index) {
        // (0 head) <-> (0) <-> (0 tail)
        // helper (head.next, 0)
        if (index == 0) {
            return n.item;
        }
        return helper(n.next, index-1);
    }
}