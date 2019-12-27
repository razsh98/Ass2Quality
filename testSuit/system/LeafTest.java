package system;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeafTest {

    FileSystem f = new FileSystem(9);

    @Test
    public void testName() {
        FileSystem f = new FileSystem(9);
        try {
            Node n = new Leaf("l",10);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getPath() {
        try {
            Node n = new Leaf("l",10);
            //name
            assertEquals(((Leaf) n).name, "l");
            //depth
            assertEquals(n.depth,0);
            //size
            assertTrue(((Leaf) n).size==3);
            //path
            String [] specPath= new String[2];
            specPath[0]="root";
            specPath[1]="n";
            assertEquals(n.getPath().toString(),specPath.toString());
        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
            System.out.println(e.getStackTrace().toString());
        }
    }
}