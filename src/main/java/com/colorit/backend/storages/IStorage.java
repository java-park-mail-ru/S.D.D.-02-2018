package com.colorit.backend.storages;

public interface IStorage {
    AbstractStorageResponse writeFile();

    AbstractStorageResponse readFile();
}
