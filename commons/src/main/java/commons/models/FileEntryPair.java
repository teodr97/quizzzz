package commons.models;

import java.io.InputStream;
import java.util.Objects;


public class FileEntryPair{
    private String fileName;
    private InputStream fileData;

    public FileEntryPair(String fileName, InputStream fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntryPair that = (FileEntryPair) o;
        return Objects.equals(fileName, that.fileName) && Objects.equals(fileData, that.fileData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, fileData);
    }

    @Override
    public String toString() {
        return "FileEntryPair{" +
                "fileName='" + fileName + '\'' +
                ", fileData=" + fileData +
                '}';
    }
}