package commons.models;

import java.io.Serializable;
import java.util.Objects;

public class FileInfo implements Serializable {
    private String encoding;
    private String pathname;
    private Boolean directory;

    /**
     * constructor method for FileInfo class
     * @param pathname : the name of the path which will be used to store the file
     * @param encoding : the file contents encoded in base64
     * @param directory : true if file is directory, false otherwise
     */
    public FileInfo(String pathname, String encoding, Boolean directory){
        this.pathname = pathname;
        this.encoding = encoding;
        this.directory = directory;
    }

    /**
     * default constructor for serialization
     */
    public FileInfo(){}


    /**
     * gets the encoding parameter
     * @return : encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * gets the pathname
     * @return : pathname
     */
    public String getPathname() {
        return pathname;
    }

    /**
     * gets the directory parameter
     * @return : directory
     */
    public Boolean getDirectory() {
        return directory;
    }

    /**
     * sets the encoding parameter
     * @param encoding : new encoding of file
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * sets the directory parameter
     * @param directory : new Boolean of directory
     */
    public void setDirectory(Boolean directory) {
        this.directory = directory;
    }

    /**
     * sets the pathname parameter
     * @param pathname : new pathname
     */
    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    /**
     * checks if two FileInfo types are equal
     * @param object : another object to check
     * @return : true if the two are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileInfo fileInfo = (FileInfo) object;
        return Objects.equals(encoding, fileInfo.encoding) && Objects.equals(pathname, fileInfo.pathname);
    }

    /**
     * creates and returns the hashCode for the object
     * @return : a hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(encoding, pathname);
    }
}
