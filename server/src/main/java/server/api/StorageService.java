package server.api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import commons.models.FileInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;



@Service
public class StorageService{

    private final Path rootLocation;

    /**
     * initialize on the images folder for resources
     */
    public StorageService() {
        this.rootLocation = Paths.get("server","src","main","resources","images");
    }

    /**
     * stores the file in a given path
     */
    public void store(FileInfo file) throws IOException {
        //assign image folder
        Path destinationFile = this.rootLocation;

        //if given file is a directory, create the directory and return it
        if(file.getDirectory()){
            Path dirPath = destinationFile.resolve(file.getPathname());
            Files.createDirectories(dirPath);
            return;
        }

        //decode received file
        byte[] decodedImage = Base64.decodeBase64(file.getEncoding());

        //get a path with the file as the path destination
        Path newDest = destinationFile.resolve(file.getPathname());

        //copy file directly
        Path p = Files.createFile(newDest);
        try(OutputStream stream = new FileOutputStream(p.toFile())){
            stream.write(decodedImage);
        }

    }

    /**
     * returns a file from path
     */
    public File retrieve(String image_path) throws FileNotFoundException {
        Path p = rootLocation.resolve(image_path);
        File f = new File(p.toUri().toString());
        if(f.exists()){
            return f;
        } else{
            throw new FileNotFoundException("No such file exists");
        }
    }


    /**
     * Deletes all files
     */
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}