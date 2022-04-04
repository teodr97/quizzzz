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
        this.rootLocation = Paths.get("server","src","main","resources","images");
    }

    /**
     * stores the file in a given path
     */
    public void store(String path) throws IOException {
        Path destinationFile = this.rootLocation;
        Path initialPath = Paths.get(path);
        File file = initialPath.toFile();

        //if its a directory create new directory
        if(file.isDirectory()){
            Path directoryDest = destinationFile.resolve(file.getName());
            File dirDestFile = directoryDest.toFile();
            dirDestFile.mkdir();
            return;
        }

        //get a path with the file as the path destination
        Path newDest = destinationFile.resolve(file.getParentFile().getName() + "\\" + file.getName());

        //otherwise, copy file directly
        Files.copy(file.toPath(),newDest,StandardCopyOption.REPLACE_EXISTING);

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