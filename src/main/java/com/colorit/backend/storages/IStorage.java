package com.colorit.backend.storages;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface IStorage {
    String writeFile(File file) throws Exception;

    byte[] readFile(String path) throws Exception;
}
