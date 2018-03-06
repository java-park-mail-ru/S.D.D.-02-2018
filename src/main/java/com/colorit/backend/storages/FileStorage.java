package com.colorit.backend.storages;

import java.util.Map;

public class FileStorage implements IStorage {
    private IStorage storage;
    private final static String HEROKU_VAR = "HEROKU_VAR";
    private final static String OS_NAME = System.getProperty("os.name");

    // TODO create storage, by detecting env_var -> heroku => then create Cloudinary, else local
    FileStorage() {
        Map<String, String> envVars = System.getenv();
        if (envVars.get(HEROKU_VAR) != null) {
            this.storage = new CloudinaryStorage();
        } else {
            this.storage = new LocalStorage();
        }
    }

    @Override
    public AbstractStorageResponse writeFile() {
        return storage.writeFile();
    }

    public AbstractStorageResponse readFile() {
        return storage.readFile();
    }
}
