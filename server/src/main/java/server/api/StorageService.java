package server.api;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
     * retrieves an image from a given image path
     * @param image_path : the image path to look for
     * @return : an encoded string containing the image
     * @throws FileNotFoundException : if file not found
     */
    public String retrieve(String image_path) throws IOException {
        System.out.println("Input: " + image_path);
        Path p = rootLocation.resolve(image_path);
        System.out.println(p);
        File file = new File(p.toString());

        //convert file to byte array
        byte[] bytes = Base64.encodeBase64(Files.readAllBytes(file.toPath()));
        String encoding = new String(bytes, StandardCharsets.US_ASCII);


        return encoding;
    }


    /**
     * Deletes all files
     */
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}