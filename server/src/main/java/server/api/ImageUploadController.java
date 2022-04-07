package server.api;


import commons.models.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.*;


@RestController
@RequestMapping("/images")
public class ImageUploadController {
    private final StorageService storageService;

    /**
     * assigns the storage service to the controller
     * @param storageService : the storageService
     */
    @Autowired
    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * takes an image path and returns an image
     * @param image_path : the image path of the activity
     * @return : the file details of the image
     * @throws FileNotFoundException : if file not found
     */
    @GetMapping(path = "/get")
    public FileInfo getImage(@RequestParam String image_path) throws IOException {
        String s = storageService.retrieve(image_path);
        FileInfo f = new FileInfo(image_path, s, false);
        return f;
    }

    /**
     * receives a string from client of a path of a file, and copies the file to the server
     * @param file : FileInfo instance with file details
     * @return : response message
     * @throws IOException : if method fails
     */
    @PostMapping(path = "/upload")
    public ResponseEntity<FileInfo> uploadImage(@RequestBody FileInfo file) throws IOException {
        //literally directly upload the file
        storageService.store(file);
        return ResponseEntity.ok(file);
    }

}


