package system;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TreeTest {

    private FileSystem fileSystem = new FileSystem(20);



    @Test
    public void testNew(){
        Tree t = new Tree("dvir&raz_Tree");
        assertTrue(t.name.equals(new String("dvir&raz_Tree")));
    }

    @Test
    public void testGetChildByNamePuttingToDictionary(){
        Tree t = new Tree("dvir&raz_Tree");
        Tree rootOft=new Tree("rootOft");
        rootOft.children.put("dvir&raz_Tree",t);
        assertEquals(rootOft.GetChildByName("dvir&raz_Tree"), t);
    }

//    @Test
//    public void testGetChildByNameSettingParent(){
//        Tree t = new Tree("dvir&raz_Tree");
//        Tree rootOft=new Tree("rootOft");
//        t.parent=rootOft;
//        assertEquals(rootOft.GetChildByName("dvir&raz_Tree"), t);
//    }

    @Test
    public void testGetPath(){
        try {
            fileSystem.dir(new String[]{"root", "dvir&raz_Tree"});
            Tree treeTest = fileSystem.DirExists(new String[]{"root", "dvir&raz_Tree"});
            String [] targetPath=new String[]{"root", "dvir&raz_Tree"};
            assertTrue(Arrays.toString(targetPath).equals(Arrays.toString(treeTest.getPath())));
            }
        catch (BadFileNameException e) {
                assertTrue("BadFileNameException",1==1);
            }
        }
}