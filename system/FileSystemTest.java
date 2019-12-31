package system;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.DirectoryNotEmptyException;

import static org.junit.Assert.*;

public class FileSystemTest {

    FileSystem fileSystem;
    int spaceCapacity;
    @Before
    public void init(){
        spaceCapacity = 20;
        fileSystem = new FileSystem(spaceCapacity);
        try {
            fileSystem.dir(new String[]{"root", "testTree"});
            fileSystem.dir(new String[]{"root", "testDir"});
            fileSystem.file(new String[]{"root", "testTree","testFile"},3);
            this.spaceCapacity=this.spaceCapacity-3;
        } catch (BadFileNameException e) {
            e.printStackTrace();
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dir() {
        String[] rootPath=new String[]{"root"};
        String [] existPath = new String[]{"root", "testTree"};
        String [] badPath = new String[]{"testTree","testFile"};
        String [] path = new String[2];
        path[0]="root";
        path[1]="dvir";

        assertFalse(fileSystem.DirExists(rootPath) == null);

        try {
            fileSystem.dir(path);
            assertTrue(fileSystem.DirExists(path)!=null);
        } catch (Exception e) {
            assertTrue(e instanceof BadFileNameException);
        }
        try{
            fileSystem.dir(badPath);
            assertFalse(1==1);
        } catch (Exception e) {
            assertTrue(e instanceof BadFileNameException);
        }
        try{
            assertTrue(fileSystem.DirExists(existPath) != null);
        } catch (Exception e) {
            assertFalse(1==1);
        }


    }

    @Test
    public void disk() {
        try{
            int counter;
            String [][] disk ;
            String []currPath ;
            counter=0;
            disk= fileSystem.disk();
            for(int i = 0 ; i< disk.length; i++ ){
                currPath=disk[i];
                if(counter<3){ assertTrue(currPath != null); }
                else{ assertTrue(currPath == null); }
                counter+=1;
            }
        }
        catch (Exception e){
            assertTrue(1==0);
        }
    }
    //
    @Test
    public void file() {
        String [] goodPathFile=new String[]{"root", "testTree", "testFile"};
        String [] goodPathFile2=new String[]{"root", "testTree", "testFile2"};
        String [] goodPathFile3=new String[]{"root", "testTree", "BigTestFile"};
        String [] badFilePath=new String[]{ "testTree", "BigTestFile"};
        try{
            fileSystem.file(goodPathFile2, 3);
            assertTrue(1==1);
        }
        catch (Exception e){
            assertTrue(1==1);
        }
        try{
            fileSystem.file(goodPathFile2, 3);
            assertTrue(1==1);
        }
        catch (Exception e){
            assertTrue(e instanceof BadFileNameException);
        }
        try{
            fileSystem.file(badFilePath, 3);
            assertTrue(1==0);
        }
        catch (Exception e){
            assertTrue(e instanceof BadFileNameException); }
        try{
            fileSystem.file(goodPathFile3,22);
            assertFalse(1==1);
        }
        catch (Exception e){
            assertTrue(e instanceof OutOfSpaceException);
        }
        try{
            assertEquals(14, FileSystem.fileStorage.countFreeSpace());
        }
        catch (Exception e){
            assertTrue(1==0);
        }
    }

    @Test
    public void lsdir() {
        String [] goodPathFile=new String[]{"root", "testTree",""};
        String [] goodPathFile2=new String[]{"root", "testTree"};
        String [] goodPathFile3=new String[]{"root", "testTree", "BigTestFile"};
        String [] badFilePath=new String[]{"root", "testTree","testFile"};
        int currSpace=FileSystem.fileStorage.countFreeSpace();
        try{
            goodPathFile[2]="fake_file";
            assertTrue(fileSystem.lsdir(goodPathFile) == null);
        }
        catch (Exception e){
            assertTrue(1==0);
        }
        try{
            String[] inside;
            inside=fileSystem.lsdir(goodPathFile2);
            int len = inside.length;
            int one = 1;
            assertEquals(one, len);
            assertEquals("testFile", inside[0]);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            String[] inside;
            inside = fileSystem.lsdir(badFilePath);
            int len = inside.length;
            int one = 1;
            assertEquals(one, len);
            assertEquals("testFile", inside[0]);
        }
        catch (Exception e){
            assertTrue(1==1 );
        }

    }

    @Test
    public void rmfile() {
        String [] goodPathFile=new String[]{"root", "testTree",""};
        String [] goodPathFile2=new String[]{"root", "testTree", "testFile"};
        String [] goodPathFile3=new String[]{"root", "testTree", "BigTestFile"};
        String [] badFilePath=new String[]{ "testTree", "BigTestFile"};
        int currSpace=FileSystem.fileStorage.countFreeSpace();
        try{
            goodPathFile[2]="fake_file";
            fileSystem.rmfile( goodPathFile);
            assertTrue(1==1);
        }
        catch (Exception e){
            assertTrue(1==0);
        }
        try{
            fileSystem.rmfile(goodPathFile2);
            currSpace += 3;
            assertEquals(currSpace, FileSystem.fileStorage.countFreeSpace());
        }
        catch (Exception e){
            assertTrue(1==0);
        }
    }

    @Test
    public void rmdir() {
        //stopped here
        String [] goodPathFile=new String[]{"root", "testTree",""};
        String [] goodPathFile2=new String[]{"root", "testTree"};
        String [] goodPathFile3=new String[]{"root", "testDir"};
        String [] badFilePath=new String[]{ "testTree", "BigTestFile"};
        goodPathFile[2]="fake_dir";
        try{
            fileSystem.rmdir(goodPathFile);
            assertTrue(1==1);
        }
        catch (Exception e){
            assertTrue(1==0);
        }
        try{
            fileSystem.rmdir(goodPathFile2);
            assertTrue(1==0);
        }
        catch (DirectoryNotEmptyException e){
            assertTrue(1==1);
        }
        catch (Exception e){
            assertTrue(1==0);
        }
        try{
            fileSystem.rmdir(goodPathFile3);
            assertTrue(fileSystem.DirExists(goodPathFile3) == null);
        }
        catch (Exception e){
            assertTrue(1==0);
        }
    }

    @Test
    public void fileExists() {

    }

    @Test
    public void dirExists() {
        String [] path2dir=new String[]{"root", "testTree"};
        String [] path2file=(new String[]{"root", "testTree","testFile"});
        assertTrue(fileSystem.DirExists(path2dir)!=null);
        assertTrue(fileSystem.DirExists(path2file)==null);
    }
}