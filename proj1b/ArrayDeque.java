public class ArrayDeque<T> implements Deque<T> {
        private T[] items;
        private int left;
        private int right;
        private int capacity = 8;
        
        public ArrayDeque() {
            items = (T[]) new Object[capacity];
            left = right = 0;
        }
    
    public boolean isFull(){
        return size()==capacity-1;
    }
    @Override
    public int size(){
        int size= (right-left+capacity)%capacity;
        return size;
    }
    @Override
    public boolean isEmpty(){
        return left==right;
    }

    public void resize(int newcapacity){
        T[] newarray=(T[]) new Object[newcapacity];
        int size=size();
        if(left<right)
            for(int i=left,j=0;i<right && j<size;i++,j++)
                newarray[j]=items[i];
        else if (left>right){
            int j=0;
            for(int i=left;j<capacity-left;i++,j++)
                newarray[j]=items[i];
            for (int i=0;j<size;i++,j++)
                newarray[j]=items[i];
        }
        left=0;
        right=size;
        items=newarray;
        capacity=newcapacity;

    }
    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if(isFull())
            resize((int)(capacity *1.5));
        left=(left-1+capacity)%capacity;
        items[left]=item;
    }
    @Override
    public void addLast(T item) {
        if(isFull())
            resize((int)(capacity *1.5));
        items[right]=item;
        right=(right+1+capacity)%capacity;
    }

    public void printDeque() {

        if(left<right)
            for (int i = left; i < right; i++) {
                if(i==right-1){
                    System.out.print(items[i]);
                    break;
                }
                System.out.print(items[i]);
                System.out.print(" ");
            }
        else if (left>right){
            for (int i = left; i < capacity; i++) {
                System.out.print(items[i]);
                System.out.print(" ");
            }
            for(int i=0;i<right;i++){
                if(i==right-1){
                    System.out.print(items[i]);
                    break;
                }
                System.out.print(items[i]);
                System.out.print(" ");
            }
        }
    }
    @Override
    public T removeFirst() {
        // (0 head) <-> (0 tail)
        if (isEmpty()) {
            return null;
        }
        T res=items[left];
        left=(left+1)%capacity;
        if(isLowUsageRate())
            resize((int) (capacity*0.5));
        return res;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        right = (right - 1 + capacity) % capacity;
        T res= items[right];
        if(isLowUsageRate())
            resize((int) (capacity*0.5));
        return res;
    }
    private boolean isLowUsageRate() {
        return capacity >= 16 && size() / (double) capacity < 0.25;
    }
    @Override
    public T get(int index) { // iterative
        if (index >= size()|| isEmpty()||index<0) {
            return null;
        }
        if(left<right)
            return items[index+left];
        else if(left>right){
            return items[(index+left)%capacity];
        }
        return null;
    }

}