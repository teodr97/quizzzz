package commons.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileInfoTest{

    FileInfo fileInfo;

    /**
     * assign instance of fileInfo
     */
    @BeforeEach
    private void initializeTests(){ fileInfo = new FileInfo("test/path", "encodedmessage", false);}

    /**
     * test the pathname getter
     */
    @Test
    void getPathname(){
        assertEquals("test/path", fileInfo.getPathname());
    }

    /**
     * test the encoding getter
     */
    @Test
    void getEncoding(){
        assertEquals("encodedmessage", fileInfo.getEncoding());
    }

    /**
     * test the directory getter
     */
    @Test
    void getDirectory(){
        assertEquals(false, fileInfo.getDirectory());
    }

    /**
     * test the pathname setter
     */
    @Test
    void setPathname(){
        fileInfo.setPathname("diff/location");
        assertEquals("diff/location", fileInfo.getPathname());
    }

    /**
     * test the encoding setter
     */
    @Test
    void setEncoding(){
        fileInfo.setEncoding("test");
        assertEquals("test", fileInfo.getEncoding());
    }

    /**
     * test the directory setter
     */
    @Test
    void setDirectory(){
        fileInfo.setDirectory(true);
        assertEquals(true, fileInfo.getDirectory());
    }

    /**
     * test method for equals
     */
    @Test
    void equals(){
        FileInfo fileInfo2 = new FileInfo("test/path", "encodedmessage", false);
        FileInfo fileInfo3 = new FileInfo("wowow","encodedmessage",false);
        assertTrue(fileInfo.equals(fileInfo2));
        assertFalse(fileInfo.equals(fileInfo3));
    }



}