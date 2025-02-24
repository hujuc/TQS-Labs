/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setA.add(99), "add: adding a duplicate element did not throw an exception.");
        assertEquals(1, setA.size(), "add: duplicate element added.");

        assertThrows(IllegalArgumentException.class, () -> setB.add(39), "add: adding an element to full set didn't throw an exception.");
        assertFalse(setB.contains(39), "add: element added to full set.");
        assertEquals(6, setB.size(), "add: element added to full set.");

        assertThrows(IllegalArgumentException.class, () -> setA.add(-1), "add: adding a negative element did not throw an exception.");
        assertEquals(1, setA.size(), "add: negative element added.");
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testIntersect() {
        assertFalse(setA.intersects(setB));
        assertFalse(setB.intersects(setA));
        assertTrue(setB.intersects(setC));
        assertTrue(setC.intersects(setB));
    }

    @Test
    void testHashCodeConsistency() {
        int hashCode1 = setA.hashCode();
        int hashCode2 = setA.hashCode();

        assertEquals(hashCode1, hashCode2, "hashCode: same object must have same hash code");
    }

    @Test
    void testEqualsNull() {
        assertFalse(setA.equals(null), "equals: setA is not null");
    }

    @Test
    void testEqualsDifferentType() {
        assertFalse(setA.equals(2), "equals: setA != 2");
    }

    @Test
    void testEqualsSelf() {
        assertEquals(setA, setA, "equals: setA must equal setA");
    }

    @Test
    void testEqualsDifferent() {
        assertNotEquals(setA, setB, "equals: setA != setB");
    }

    @Test
    void testIterator() {
        int[] elems = new int[]{10, 20, 30, 40, 50, 60};
        int i = 0;
        for (int elem : setB) {
            assertEquals(elems[i], elem, "iterator: element mismatch");
            i++;
        }
    }

    @Test
    public void testAddNonNaturalNumber() {
        assertThrows(IllegalArgumentException.class, () -> setA.add(0), "Adding zero should throw an exception");
        assertThrows(IllegalArgumentException.class, () -> setA.add(-10), "Adding negative number should throw an exception");
    }

    @Test
    public void testAddDuplicateElement() {
        BoundedSetOfNaturals set = new BoundedSetOfNaturals(5);  // Criar um conjunto com mais espaço disponível
        set.add(10);
        assertThrows(IllegalArgumentException.class, () -> set.add(10), "Adding duplicate element should throw an exception");
    }

}
