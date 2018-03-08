package com.colorit.backend.storages;

import com.colorit.backend.storages.responses.AbstractStorageResponse;

import java.io.File;

public interface IStorage {
    String writeFile(File file) throws Exception;

    byte[] readFile(String path) throws Exception;
}
