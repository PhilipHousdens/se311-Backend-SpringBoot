package se331.rest.controller;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import se331.rest.util.CloudStorageHelper;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class BucketController {
    Dotenv dotenv = Dotenv.load();
    public String bucketName = dotenv.get("BUCKET_NAME");
    final CloudStorageHelper cloudStorageHelper;
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException, ServletException {
        return ResponseEntity.ok(this.cloudStorageHelper.getImageUrl(file, bucketName));
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> upLoadFileComponent(@RequestParam(value = "image") MultipartFile file) throws IOException, ServletException {
        return ResponseEntity.ok(this.cloudStorageHelper.getStorageFileDto(file, bucketName));
    }
}
