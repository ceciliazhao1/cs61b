import static org.junit.Assert.*;
import org.junit.Test;



public class TestArrayDequeGold {
    @Test
    public static void test() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

        StringBuilder msg = new StringBuilder();

        int s = 0;
        for (int i = 0; i < 500; i ++) {
            if (i % 5 == 0) {
                msg.append("size()\n");
                assertEquals(msg.toString(), ads.size(), sad.size());
            }
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
            } else {
                sad1.addFirst(i);
            }
        }

        sad1.printDeque();
    }
}
