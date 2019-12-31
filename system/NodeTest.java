package system;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;

public class NodeTest {

    Random rnd;

    @Before
    public void initialize() {
        rnd = new Random();
    }

    @Test
    public void checkPath() {
        int dep;

        String uniqeName;
        LinkedList<Tree> trees;
        String[] path;
        Tree tree;
        Node prev;
        String treeName;
        Node testedTree;
        String testName;

        dep= rnd.nextInt(100);

        uniqeName= "UniqueNodeName";

        testedTree= new Tree(uniqeName);

        prev = testedTree;


        int currDepth = dep + 1;
        testedTree.depth = currDepth;
        trees = new LinkedList<>();
        for (int i = 0; i < dep; i++) {
            boolean sameTree;
            treeName = "treeName "+i;
            tree = new Tree(treeName);
            prev.parent = tree;
            tree.depth = prev.depth - 1;
            prev = tree;
            sameTree= prev instanceof Tree;
            if (sameTree)
                trees.add(0,(Tree) prev);
        }

        path = testedTree.getPath();
        assertTrue(path.length==dep + 1);

        for (int i = 0; i < trees.size(); i++) {
            assertEquals(trees.get(i).name, path[i]);
        }
        testName=path[path.length - 1];
        assertTrue(testName.equals( uniqeName));

    }
}





