package com.colorit.backend.storages.storageimpls;

import com.colorit.backend.storages.IStorage;
import com.colorit.backend.storages.responses.AbstractStorageResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;



public class LocalStorage implements IStorage {
    private String staticContentPath;
    private MessageDigest md5;
    public LocalStorage(String userHome) {
        staticContentPath = userHome + "/static";
        try {
            md5 = java.security.MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public AbstractStorageResponse writeFile(File file) {
        String data = "ddd";
        byte[] d = data.getBytes();
        byte[] out = md5.digest(d);

        return null;
    }

    @Override
    public AbstractStorageResponse readFile(String path) {
        return null;
    }
}
