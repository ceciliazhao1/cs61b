// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    public int first;
    public int last;
    public T [] rb;
        /**
         * Create a new ArrayRingBuffer with the given capacity.
         */
        ArrayRingBuffer(int capacity) {
            // TODO: Create new array with capacity elements.
            //       first, last, and fillCount should all be set to 0.
            //       this.capacity should be set appropriately. Note that the local variable
            //       here shadows the field we inherit from AbstractBoundedQueue, so
            //       you'll need to use this.capacity to set the capacity.
            rb= (T[])new Object[capacity];
            first=last=fillCount=0;
            this.capacity=capacity;
        }

        /**
         * Adds x to the end of the ring buffer. If there is no room, then
         * throw new RuntimeException("Ring buffer overflow"). Exceptions
         * covered Monday.
         */
        public void enqueue(T x) {
            // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
            if(isFull()) throw new RuntimeException("Ring buffer overflow");
            rb[last]=x;
            last=(last+1)%capacity;
            fillCount++;
        }

        /**
         * Dequeue oldest item in the ring buffer. If the buffer is empty, then
         * throw new RuntimeException("Ring buffer underflow"). Exceptions
         * covered Monday.
         */
        public T dequeue() {
            // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
            if(isEmpty()) throw new RuntimeException("Ring buffer underflow");
            T cur= this.peek();
            first=(first+1)%capacity;
            fillCount--;
            return cur;
        }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer Overflow");
        }
        return rb[first];
    }

        // TODO: When you get to part 5, implement the needed code to support iteration.
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
    public class ArrayIterator<T> implements Iterator{
            public int fir;
            public int count;
        ArrayIterator(){
            fir=first;
            count=0;
        }
            @Override
            public T next(){
                T renum=(T)rb[fir];
                fir=(fir+1)%capacity;
                count++;
                return renum;
            }
            @Override
            public boolean hasNext(){
                if(count<fillCount) return true;
                else return false;
            }
    }

}
