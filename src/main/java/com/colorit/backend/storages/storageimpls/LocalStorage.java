package com.colorit.backend.storages.storageimpls;

import com.colorit.backend.storages.IStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;

@Component
@ConditionalOnExpression("'${heroku_var}' != 'true'")
public class LocalStorage implements IStorage {
    private final String staticContentPath;
    private MessageDigest md5;

    public LocalStorage() {
        final String userHome = System.getenv("os.home");
        staticContentPath = userHome + "/static";
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException nSAe) {
            throw new RuntimeException(nSAe.getCause());
        }
    }

    // todo implement while deploy on standalone server
    @Override
    public String writeFile(File file) {
        return null;
    }

    // todo implement while deploy on standalone server
    @Override
    public byte[] readFile(String path) {
        return null;
    }
}
