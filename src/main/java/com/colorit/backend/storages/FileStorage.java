package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.StorageResponse;
import com.colorit.backend.storages.statuses.StorageStatus;
import com.colorit.backend.storages.storageimpls.CloudinaryStorage;
import com.colorit.backend.storages.storageimpls.LocalStorage;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

@Component
public class FileStorage {
    @Autowired
    private IStorage storage;
    private static final String HEROKU_VAR = "HEROKU_VAR";
    private static final String USER_HOME = System.getProperty("os.name");

    // TODO create storage, by detecting env_var -> heroku => then create Cloudinary, else local
    FileStorage() {
        Map<String, String> envVars = System.getenv();
        if (envVars.get(HEROKU_VAR) != null) {
            this.storage = new CloudinaryStorage();
        } else {

            this.storage = new LocalStorage(USER_HOME);
        }
    }

    private String getFileContent(File file) throws Exception {
        // TODO correctly check exceptions
        try (InputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is);) {
            AutoDetectParser parser = new AutoDetectParser();
            Detector detector = parser.getDetector();
            Metadata md = new Metadata();
            MediaType mediaType = detector.detect(bis, md);
            return mediaType.getType();
        }
    }

    public StorageResponse saveImage(MultipartFile multipartFile) {
        StorageResponse<String> response = new StorageResponse<>();
        try {
            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            if (!getFileContent(file).equals("image")) {
                return null; // TODO ERROR Incorrect file
            }
            multipartFile.transferTo(file);
            String name = storage.writeFile(file);
            response.setStatus(StorageStatus.OK_STATE);
            response.setData(name);
        } catch (Exception ex) {

        }
        return null;
    }

    public StorageResponse saveFile(MultipartFile multipartFile) {
        StorageResponse<String> response = new StorageResponse<>();
        try {
            // TODO check file is image
            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            multipartFile.transferTo(file);
            String name = storage.writeFile(file);
            response.setStatus(StorageStatus.OK_STATE);
            response.setData(name);
        } catch (Exception e) {
            response.setStatus(StorageStatus.ERROR_STATE);
        }
        return response;
    }
}
