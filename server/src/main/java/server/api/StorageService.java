package server.api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import commons.game.exceptions.ImageUploadException;
import commons.models.FileEntryPair;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageService{

    private final Path rootLocation;

    public StorageService() {
        this.rootLocation = Paths.get("src","main","resources","images");
    }

    /**
     * stores the file in a given path
     */
    public void store(String path) throws IOException {
        System.out.println("StorageService method: " + path);
//        Path destinationFile = this.rootLocation;
//        Path initialPath = Paths.get(path);
//        File file = initialPath.toFile();
//
//        //get a path with the file as the path destination
//        Path newDest = destinationFile.resolve(file.getName());
//        System.out.println(newDest.toUri().toString());
//
//        File newDestFile = newDest.toFile();
//
//        //if its a directory create new directory
//        if(file.isDirectory()){
//            newDestFile.mkdir();
//            return;
//        }
//
//        //otherwise convert ZipEntry to InputStream and copy file
//        Files.copy(file.toPath(),newDest,StandardCopyOption.REPLACE_EXISTING);

    }

    /**
     * returns a path in relation to the root location
     * @param filename
     * @return
     */
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * returns a file from path
     */
    public File retrieve(Path path) throws FileNotFoundException {
        File f = new File(path.toUri().toString());
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