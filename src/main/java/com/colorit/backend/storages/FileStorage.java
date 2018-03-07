package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.AbstractStorageResponse;
import com.colorit.backend.storages.storageimpls.CloudinaryStorage;
import com.colorit.backend.storages.storageimpls.LocalStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Component
public class FileStorage implements IStorage {
    private IStorage storage;
    private final static String HEROKU_VAR = "HEROKU_VAR";
    private final static String USER_HOME = System.getProperty("os.name");

    // TODO create storage, by detecting env_var -> heroku => then create Cloudinary, else local
    FileStorage() {
        Map<String, String> envVars = System.getenv();
        if (envVars.get(HEROKU_VAR) != null) {
            this.storage = new CloudinaryStorage();
        } else {

            this.storage = new LocalStorage(USER_HOME);
        }
    }

    @Override
    public AbstractStorageResponse writeFile(File file) {
        return storage.writeFile(file);
    }

    public AbstractStorageResponse readFile(String path) {
        return storage.readFile(path);
    }

    public AbstractStorageResponse saveFile(MultipartFile multipartFile) {
        try {
            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            multipartFile.transferTo(file);

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
