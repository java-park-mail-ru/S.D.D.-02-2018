package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.AbstractStorageResponse;

import java.io.File;

public interface IStorage {
    AbstractStorageResponse writeFile(File file);

    AbstractStorageResponse readFile(String path);
}
