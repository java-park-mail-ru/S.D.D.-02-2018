package com.colorit.backend.storages;

import java.io.File;

public interface IStorage {
    String writeFile(File file) throws Exception;

    byte[] readFile(String path) throws Exception;
}
