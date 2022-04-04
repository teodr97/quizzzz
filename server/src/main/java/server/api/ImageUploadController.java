package server.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
import java.io.*;
import java.nio.file.Path;


@RestController
@RequestMapping("/images")
public class ImageUploadController {
    private final StorageService storageService;

    @Autowired
    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(path = "/get/{image_path}")
    public File getImage(@PathParam("image_path")String image_path) throws FileNotFoundException {
        return storageService.retrieve(image_path);
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadImage(@RequestBody String path) throws IOException {
        //literally directly upload the file
        storageService.store(path);
        return ResponseEntity.ok(path);
    }

}


