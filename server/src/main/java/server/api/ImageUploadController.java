package server.api;


import commons.models.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
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
     * @param image_path : the image path of file
     * @return : the file of the image
     * @throws FileNotFoundException : if file not found
     */
    @GetMapping(path = "/get/{image_path}")
    public File getImage(@PathParam("image_path")String image_path) throws FileNotFoundException {
        return storageService.retrieve(image_path);
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


