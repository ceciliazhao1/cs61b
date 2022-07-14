/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // modify into sum counts
        for (int i = 1; i < counts.length; i += 1) {
            counts[i]+=counts[i-1];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {

            sorted2[counts[arr[i]]-1] = arr[i];
            --counts[arr[i]];
        }

        // return the sorted array
        return sorted2;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        // find max
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
            min = min < i ? min : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max - min + 1];
        for (int i : arr) {
            counts[i- min]++;
        }

        // however, below is a more proper, generalized implementation of
        // modify into sum counts
        for (int i = 1; i < counts.length; i += 1) {
            counts[i]+=counts[i-1];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {

            sorted2[counts[arr[i]-min]-1] = arr[i];
            --counts[arr[i]-min];
        }

        // return the sorted array
        return sorted2;
    }
    //第二种方法就是整体-最大负数；
    public static int[] betterCountingSort2(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        // find min negative
        int minNegative = 0;
        for (int i : arr) {
            if (i < 0) {
                minNegative = Math.max(minNegative, Math.abs(i));
            }
        }
        //找到最大负数，然后求出来
        minNegative = -minNegative;

        //make every number positive
        int[] arrCopy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrCopy[i] = arr[i] - minNegative;
        }

        arrCopy = naiveCountingSort(arrCopy);

        // recover the sorted array by adding min negative
        for (int i = 0; i < arr.length; i++) {
            arrCopy[i] = arrCopy[i] + minNegative;
        }
        return arrCopy;
    }
}
