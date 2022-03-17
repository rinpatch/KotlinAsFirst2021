package lesson12.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class OpenHashSetTest {

    @Test
    @Tag("6")
    fun size() {
        val set = OpenHashSet<Int>(32)
        assertEquals(32, set.capacity)
        assertEquals(0, set.size)
        set.add(42)
        set.add(42)
        assertEquals(1, set.size)
        set.add(21)
        assertEquals(2, set.size)
    }

    @Test
    @Tag("4")
    fun isEmpty() {
        val set = OpenHashSet<Int>(16)
        assertTrue(set.isEmpty())
        set.add(0)
        assertFalse(set.isEmpty())
    }

    @Test
    @Tag("10")
    fun add() {
        val set = OpenHashSet<String>(4)
        assertTrue(set.add("alpha"))
        assertTrue(set.add("beta"))
        assertFalse(set.add("alpha"))
        assertTrue(set.add("gamma"))
        assertTrue(set.add("omega"))
        for (word in listOf("alpha", "beta", "gamma", "omega")) {
            assertTrue(word in set.elements)
        }
        assertFalse(set.add("???"))
        assertEquals(4, set.size)
    }

    @Test
    fun linearProbeTest() {
        val set = OpenHashSet<Int>(3)
        // Sanity check
        // Depends on how OpenHashSet.hash is implemented
        assertEquals(8.hashCode() % 3, 2.hashCode() % 3)
        assertTrue(set.add(8))
        assertTrue(set.add(2))
        assertTrue(set.contains(8))
        assertTrue(set.contains(2))
    }
    @Test
    @Tag("10")
    fun contains() {
        val set = OpenHashSet<Int>(8)
        set.add(1)
        set.add(3)
        set.add(6)
        assertTrue(3 in set)
        assertFalse(4 in set)
    }

    @Test
    @Tag("8")
    fun testEquals() {
        val set1 = OpenHashSet<Int>(8)
        set1.add(1)
        set1.add(3)
        set1.add(6)
        val set2 = OpenHashSet<Int>(4)
        set2.add(1)
        set2.add(3)
        set2.add(6)
        assertTrue(set1 == set2)
        set1.add(10)
        assertFalse(set1 == set2)
    }

    @Test
    @Tag("6")
    fun testHashCode() {
        val set1 = OpenHashSet<Int>(8)
        set1.add(1)
        set1.add(3)
        set1.add(6)
        val set2 = OpenHashSet<Int>(4)
        set2.add(1)
        set2.add(3)
        set2.add(6)
        assertTrue(set1.hashCode() == set2.hashCode())
    }
}