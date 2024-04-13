package co.icesi.edu.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashTableTest {
    private HashTable<Integer, String> emptyTable;
    private HashTable<Integer, String> fullTable;

    @Before
    public void setupEmpty() {
        emptyTable = new HashTable<>();
    }

    @Before
    public void setupFull() {
        fullTable = new HashTable<>();
        for (int i = 0; i < 60; i++) {
            fullTable.put(i, "Card" + i);
        }
    }

    @Test
    public void testPutStandard() {
        emptyTable.put(1, "Blue 3");
        Assert.assertEquals("Blue 3", emptyTable.get(1));
    }

    @Test
    public void testPutCollision() {
        Integer key1 = 1;
        Integer key2 = 38; // en la función de hash (key.hashCode() % 37) coloca ambas en el mismo índice

        emptyTable.put(key1, "Red Reverse");
        emptyTable.put(key2, "Green Draw Two");

        Assert.assertEquals("Red Reverse", emptyTable.get(key1));
        Assert.assertEquals("Green Draw Two", emptyTable.get(key2));
        Assert.assertEquals(2, emptyTable.size());
    }


    @Test
    public void testPutInteresting() {
        emptyTable.put(1, "Red Skip");
        emptyTable.put(1, "Yellow 7");  // Test updating existing key
        Assert.assertEquals("Yellow 7", emptyTable.get(1));
    }

    @Test
    public void testGetStandard() {
        fullTable.put(100, "Wild Draw Four");
        Assert.assertEquals("Wild Draw Four", fullTable.get(100));
    }

    @Test
    public void testGetBoundary() {
        Assert.assertNull(fullTable.get(-1));  // Key that does not exist
    }

    @Test
    public void testGetInteresting() {
        Assert.assertNull(emptyTable.get(1));  // Fetching from empty table
    }

    @Test
    public void testRemoveStandard() {
        fullTable.remove(10);
        Assert.assertNull(fullTable.get(10));
    }

    @Test
    public void testRemoveBoundary() {
        fullTable.remove(null);  // Assuming implementation allows removing null keys
        Assert.assertNull(fullTable.get(null));
    }

    @Test
    public void testRemoveInteresting() {
        fullTable.put(1, null);  // Inserting null value
        fullTable.remove(1);
        Assert.assertNull(fullTable.get(1));
    }

    @Test
    public void testIsEmptyStandard() {
        Assert.assertTrue(emptyTable.isEmpty());
    }

    @Test
    public void testIsEmptyBoundary() {
        emptyTable.put(1, "Blue 7");
        emptyTable.remove(1);
        Assert.assertTrue(emptyTable.isEmpty());
    }

    @Test
    public void testSizeStandard() {
        Assert.assertEquals(60, fullTable.size());
    }

    @Test
    public void testSizeBoundary() {
        emptyTable.put(1, "Green Reverse");
        Assert.assertEquals(1, emptyTable.size());
        emptyTable.remove(1);
        Assert.assertEquals(0, emptyTable.size());
    }
}

