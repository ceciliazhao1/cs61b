package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(5);
        assertTrue(arb.isEmpty());
        for (int i = 0; i < 3; i++) {
            arb.enqueue(i);
        }
        assertEquals(0, (int) arb.peek());
        assertEquals(0, (int) arb.dequeue());
        arb.dequeue();
        for (int i = 303; i < 307; i++) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
        assertEquals(2, (int) arb.dequeue());

        List<Integer> expected = Arrays.asList(303, 304, 305, 306);
        List<Integer> actual = new ArrayList<>();
        for (Integer item : arb) {
            actual.add(item);
        }
        assertEquals(expected, actual)；
    }
    
    @Test
    public void testCapacity() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(9);
        assertEquals(9, arb.capacity());
        assertEquals(0, arb.fillCount());
    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        assertTrue(arb.isEmpty());
        arb.enqueue(3);
        assertEquals(1,arb.fillCount());
        arb.enqueue(2);
        arb.enqueue(1);
        assertEquals(3, arb.fillCount());
        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());

    }
    @Test(expected = RuntimeException.class)
    public void testFullEnqueueException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(1);
        arb.enqueue(1);
        arb.enqueue(2);
    }

    @Test
    public void testDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, (int) arb.dequeue());
        assertEquals(2, arb.fillCount());

        assertEquals(2, (int) arb.dequeue());
        assertEquals(1, arb.fillCount());

        assertNotEquals(2, (int) arb.dequeue());
        assertEquals(0, arb.fillCount());
    }

    @Test(expected = RuntimeException.class)
    public void testEmptyDequeueException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(1);
        arb.dequeue();
    }

    @Test
    public void testPeek() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        arb.enqueue(1);
        assertEquals(1, (int) arb.peek());

        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, (int) arb.peek());

        arb.dequeue();
        assertEquals(2, (int) arb.peek());

    }

    @Test(expected = RuntimeException.class)
    public void testEmptyPeekException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(1);
        arb.peek();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeletePeekException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(1);
        arb.enqueue(1);
        arb.peek();

        arb.dequeue();
        arb.peek();
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<String> srb = new ArrayRingBuffer<>(5);
        srb.enqueue("writer ");
        srb.enqueue("is ");
        srb.enqueue("what ");
        srb.enqueue("I ");
        srb.enqueue("am");
        StringBuilder actual = new StringBuilder();
        for (String s : srb) {
            actual.append(s);
            System.out.print(s);
        }

        assertEquals("writer is what I am", actual.toString());

        actual.setLength(0);

        srb.dequeue();
//        System.out.println();
//        System.out.println("++++++++++++++++++++++++++");
//        for (String s : srb) {
//            actual.append(s);
//            System.out.print(s);
//        }
//        System.out.println();
//        System.out.println(actual);
//
//        System.out.println();
//        System.out.println("++++++++++++++++++++++++++");
        srb.enqueue(" Astronaut");
        for (String s : srb) {
            actual.append(s);
            System.out.print(s);
        }
        System.out.println();
        System.out.println(actual);

        assertEquals("is what I am Astronaut", actual.toString());
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer<>(5);
        arb1.enqueue(1);
        arb1.enqueue(2);
        arb1.enqueue(3);

        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(5);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);

        assertEquals(arb1, arb2);

        ArrayRingBuffer<Integer> arb3 = new ArrayRingBuffer<>(9);
        arb3.enqueue(1);
        arb3.enqueue(2);
        arb3.enqueue(3);

        assertEquals(arb1, arb3);
        assertEquals(arb2, arb3);

        ArrayRingBuffer<Integer> arb4 = new ArrayRingBuffer<>(5);
        arb3.enqueue(3);
        arb3.enqueue(2);
        arb3.enqueue(1);

        assertNotEquals(arb1, arb4);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
