package system;

import jdk.nashorn.internal.AssertsEnabled;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    public int spaceCapacity;
    public FileSystem fileSystem;

    /**
     * Quick notice:
     * We have decided not to test inconsistencies between Leaf's size attribute and Alloc()'s size argument.
     * That is because there is only 1 usage of Alloc in the original code, and in that single use,
     * Leaf passes this.size to Alloc() as an argument. Therefore inconsistencies in the wild are impossible.
     */

    @Before
    public void init(){
        spaceCapacity = 20;
        fileSystem = new FileSystem(spaceCapacity);
    }

    public void assertAllFreeOnInit(){
        assertEquals(FileSystem.fileStorage.countFreeSpace(),spaceCapacity);
    }

    public void assertOOSEOnLargeFileAlloc(){
        int filesize = spaceCapacity + 1;
        String filename = "leaf";

        try {
            Leaf leaf = new Leaf(filename,filesize);
        } catch (Exception e) {
            assertTrue(e instanceof OutOfSpaceException);
        }
    }

    public void assertCorrectFreeSpaceOnAlloc(){
        int initialFreeBlockCount = FileSystem.fileStorage.countFreeSpace();
        int filesize = 3;
        String filename = "leafyishere";

        try {

            Leaf leaf = new Leaf(filename,filesize);
            int currentFreeBlockCount = FileSystem.fileStorage.countFreeSpace();
            assertEquals(initialFreeBlockCount - filesize,currentFreeBlockCount);

        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
        }
    }

    public void assertCorrectFreeSpaceOnDealloc(){
        int filesize = 5;
        String filename = "leaflet.js";

        try {

            Leaf leaf = new Leaf(filename,filesize);
            int initialFreeBlockCount = FileSystem.fileStorage.countFreeSpace();

            FileSystem.fileStorage.Dealloc(leaf);

            int currentFreeBlockCount = FileSystem.fileStorage.countFreeSpace();
            assertEquals(initialFreeBlockCount + filesize,currentFreeBlockCount);

        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
        }
    }

    private void assertGetAllocAddsLeafToMemory() {
        int filesize = 4;
        String filename = "leafster";

        try {

            Leaf leaf = new Leaf(filename,filesize);
            Leaf[] memory = FileSystem.fileStorage.getAlloc();
            for(int i: leaf.allocations){
                assertEquals(leaf.name,memory[i].name);
            }

        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
        }
    }

    @Test
    public void testCtor(){
        assertAllFreeOnInit();
    }

    @Test
    public void alloc() {
        assertCorrectFreeSpaceOnAlloc();
        assertOOSEOnLargeFileAlloc();
    }

    @Test
    public void dealloc() {
        assertCorrectFreeSpaceOnDealloc();
    }

    @Test
    public void countFreeSpace() {
    }

    @Test
    public void getAlloc() {
        assertGetAllocAddsLeafToMemory();
    }
}