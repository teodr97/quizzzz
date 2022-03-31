package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import server.database.StorageService;

import java.util.List;

@Controller
public class ImageUploadController {
    private final StorageService storageService;

    @Autowired
    public ImageUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public List<MultipartFile> getImages(){
    }

}


