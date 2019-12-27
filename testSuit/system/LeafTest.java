package system;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LeafTest {

    FileSystem f = new FileSystem(9);

    @Test
    public void testName() {
        try {
            Node n = new Leaf("l",1);
            assertEquals(((Leaf) n).name, "l");
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSize() {
        try {
            Node n = new Leaf("l",1);
            assertEquals(((Leaf) n).size, 1);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPath() {
        try {
            Node n = new Leaf("n",1);
            String [] specPath= new String[2];
            specPath[0]="root";
            specPath[1]="n";
            String s= Arrays.toString(specPath);
            String s1= Arrays.toString(n.getPath());
            assertEquals(s,s1);
        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
            System.out.println(e.getStackTrace().toString());
        }
    }

    @Test
    public void testOverAllocate() {
        try {
            Node n = new Leaf("l",10);
        } catch (Exception e) {
            assertFalse(e instanceof OutOfSpaceException);
        }
    }




}