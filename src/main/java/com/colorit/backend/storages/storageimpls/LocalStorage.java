package com.colorit.backend.storages.storageimpls;

import com.colorit.backend.storages.IStorage;
import com.colorit.backend.storages.responses.AbstractStorageResponse;

public class LocalStorage implements IStorage {
    private String staticContentPath;

    public LocalStorage(String userHome) {
        staticContentPath = userHome + "/static";
    }

    @Override
    public AbstractStorageResponse writeFile() {
        return null;
    }

    @Override
    public AbstractStorageResponse readFile() {
        return null;
    }
}
