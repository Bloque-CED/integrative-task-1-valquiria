package co.icesi.edu.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {
    private PriorityQueue<Integer> pq;

    @Before
    public void setUp() {
        pq = new PriorityQueue<>();
    }

    @Test
    public void testEnqueueStandard() {
        pq.enqueue(5, 1);
        Assert.assertEquals(5, (int)pq.peek());
    }

    @Test
    public void testEnqueueBoundary() {
        pq.enqueue(5, Integer.MAX_VALUE);
        Assert.assertEquals(5, (int)pq.peek());
    }

    @Test
    public void testEnqueueInteresting() {
        pq.enqueue(5, 2);
        pq.enqueue(10, 1);
        pq.enqueue(15, 3);
        Assert.assertEquals(15, (int)pq.peek());
    }

    @Test
    public void testDequeueStandard() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 2);
        int result = pq.dequeue();
        Assert.assertEquals(10, result);
    }

    @Test
    public void testDequeueBoundary() {
        pq.enqueue(5, 1);
        int result = pq.dequeue();
        Assert.assertTrue(pq.isEmpty());
    }

    @Test
    public void testDequeueInteresting() {
        pq.enqueue(5, 2);
        pq.enqueue(10, 1);
        pq.enqueue(15, 3);
        pq.dequeue();
        Assert.assertEquals(5, (int)pq.peek());
    }

    @Test
    public void testPeekStandard() {
        pq.enqueue(5, 1);
        int result = pq.peek();
        Assert.assertEquals(5, result);
    }

    @Test
    public void testPeekBoundary() {
        Assert.assertThrows(IllegalStateException.class, () -> pq.peek());
    }

    @Test
    public void testPeekInteresting() {
        pq.enqueue(5, 2);
        pq.enqueue(10, 1);
        int result = pq.peek();
        Assert.assertEquals(5, result);
    }

    @Test
    public void testIsEmptyStandard() {
        Assert.assertTrue(pq.isEmpty());
    }

    @Test
    public void testIsEmptyBoundary() {
        pq.enqueue(5, 1);
        pq.dequeue();
        Assert.assertTrue(pq.isEmpty());
    }

    @Test
    public void testIsEmptyInteresting() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 2);
        pq.dequeue();
        pq.dequeue();
        Assert.assertTrue(pq.isEmpty());
    }

    @Test
    public void testSizeStandard() {
        pq.enqueue(5, 1);
        Assert.assertEquals(1, pq.size());
    }

    @Test
    public void testSizeBoundary() {
        for (int i = 0; i < 1000; i++) {
            pq.enqueue(i, i % 10);
        }
        Assert.assertEquals(1000, pq.size());
    }

    @Test
    public void testSizeInteresting() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 2);
        pq.dequeue();
        Assert.assertEquals(1, pq.size());
    }

    @Test
    public void testIncreasePriorityStandard() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 2);
        pq.increasePriority();
        Assert.assertEquals(10, (int)pq.peek());
    }

    @Test
    public void testIncreasePriorityBoundary() {
        pq.enqueue(5, Integer.MAX_VALUE - 1);
        pq.increasePriority();
        Assert.assertEquals(5, (int)pq.peek());
    }

    @Test
    public void testIncreasePriorityInteresting() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 1);
        pq.increasePriority();
        Assert.assertTrue(pq.dequeue() == 5 && pq.peek() == 10);
    }

    @Test
    public void testPrioritizeLowestStandard() {
        pq.enqueue(5, 3);
        pq.enqueue(10, 1);
        pq.enqueue(15, 2);
        pq.prioritizeLowest();
        Assert.assertEquals(10, (int)pq.peek());
    }

    @Test
    public void testPrioritizeLowestBoundary() {
        pq.enqueue(5, 1);
        pq.prioritizeLowest();
        Assert.assertEquals(5, (int)pq.peek());
    }

    @Test
    public void testPrioritizeLowestInteresting() {
        pq.enqueue(5, 1);
        pq.enqueue(10, 2);
        pq.enqueue(15, 1);
        pq.prioritizeLowest();
        Assert.assertTrue(pq.peek() == 5 || pq.peek() == 15);
    }

}