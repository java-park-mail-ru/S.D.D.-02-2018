package com.colorit.backend.storages;

public interface IStorage {
    AbstractServiceResponse writeFile();

    AbstractServiceResponse readFile();
}
