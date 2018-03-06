package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.AbstractStorageResponse;

public interface IStorage {
    AbstractStorageResponse writeFile();

    AbstractStorageResponse readFile();
}
