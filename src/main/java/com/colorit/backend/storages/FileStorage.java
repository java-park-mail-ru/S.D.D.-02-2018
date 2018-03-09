package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.AbstractStorageResponse;
import com.colorit.backend.storages.statuses.StorageStatus;
import com.colorit.backend.storages.storageimpls.CloudinaryStorage;
import com.colorit.backend.storages.storageimpls.LocalStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Primary
@Component
public class FileStorage {
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

    public AbstractStorageResponse saveFile(MultipartFile multipartFile) {
        AbstractStorageResponse<String> response = new AbstractStorageResponse<>();
        try {
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
