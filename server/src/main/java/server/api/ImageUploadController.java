package server.api;

import commons.models.FileEntryPair;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
        Path actualImagePath = storageService.load(image_path);
        return storageService.retrieve(actualImagePath);
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadImage(@RequestBody String path) throws IOException {
        //literally directly upload the file
        System.out.println("uploadImage method: " + path);
        storageService.store(path);
        return ResponseEntity.ok(path);
    }

}


