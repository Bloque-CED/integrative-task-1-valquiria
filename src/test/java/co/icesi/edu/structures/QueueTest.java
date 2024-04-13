package co.icesi.edu.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueueTest {

    private Queue<Integer> queue;

    @Before
    public void setUp() {
        queue = new Queue<>();
    }


    // Test para enqueue
    @Test
    public void testEnqueueStandard() {
        queue.enqueue(5);
        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void testEnqueueBoundary() {
        queue.enqueue(Integer.MAX_VALUE);
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), queue.dequeue());
    }

    @Test
    public void testEnqueueInteresting() {
        queue.enqueue(10);
        queue.enqueue(20);
        Assert.assertEquals(Integer.valueOf(10), queue.dequeue());
        Assert.assertEquals(Integer.valueOf(20), queue.dequeue());
    }

    // Test para dequeue
    @Test
    public void testDequeueStandard() {
        queue.enqueue(1);
        queue.enqueue(2);
        Assert.assertEquals(Integer.valueOf(1), queue.dequeue());
        Assert.assertEquals(Integer.valueOf(2), queue.dequeue());
        Assert.assertTrue(queue.isEmpty());
    }


    @Test(expected = IllegalStateException.class)
    public void testDequeueBoundary() {
        queue.dequeue(); // This should throw IllegalStateException as the queue is empty.
    }

    @Test
    public void testDequeueInteresting() {
        queue.enqueue(5);
        queue.enqueue(10);
        queue.dequeue(); // Remove 5
        queue.enqueue(15);
        Assert.assertEquals(Integer.valueOf(10), queue.dequeue());
    }

    // Test para isEmpty
    @Test
    public void testIsEmptyStandard() {
        Assert.assertTrue(queue.isEmpty());
        queue.enqueue(1);
        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void testIsEmptyBoundary() {
        Assert.assertTrue(queue.isEmpty());
        queue.enqueue(5);
        queue.dequeue();
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void testIsEmptyInteresting() {
        for (int i = 0; i < 1000; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 1000; i++) {
            queue.dequeue();
        }
        Assert.assertTrue(queue.isEmpty());
    }
}