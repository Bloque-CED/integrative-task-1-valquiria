package co.icesi.edu.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackTest {
    private Stack<String> stack;

    @Before
    public void setUp() {
        stack = new Stack<>();
    }

    // Tests for push method
    @Test
    public void testPushStandard() {
        stack.push("Blue 3");
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals("Blue 3", stack.peek());
    }
    @Test
    public void testPushBoundary() {
        stack.push(null);
        Assert.assertFalse(stack.isEmpty());
        Assert.assertNull(stack.peek());
    }

    @Test
    public void testPushInteresting() {
        stack.push("Blue 3");
        stack.push("Red Skip");
        Assert.assertEquals("Red Skip", stack.peek());
    }

    // Tests for pop method
    @Test(expected = IllegalStateException.class)
    public void testPopEmptyStack() {
        stack.pop();
    }

    @Test
    public void testPopStandard() {
        stack.push("Green Reverse");
        String item = stack.pop();
        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals("Green Reverse", item);
    }

    @Test
    public void testPopInteresting() {
        stack.push("Yellow 2");
        stack.push("Red Draw Two");
        stack.pop();
        Assert.assertEquals("Yellow 2", stack.peek());
    }

    // Tests for peek method
    @Test(expected = IllegalStateException.class)
    public void testPeekEmptyStack() {
        stack.peek();
    }

    @Test
    public void testPeekStandard() {
        stack.push("Red 7");
        String item = stack.peek();
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals("Red 7", item);
    }

    @Test
    public void testPeekInteresting() {
        stack.push("Blue Skip");
        stack.push("Green 4");
        stack.peek();
        Assert.assertEquals("Green 4", stack.peek());
    }

    // Tests for isEmpty method
    @Test
    public void testIsEmptyTrue() {
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        stack.push("Red 0");
        Assert.assertFalse(stack.isEmpty());
    }
}