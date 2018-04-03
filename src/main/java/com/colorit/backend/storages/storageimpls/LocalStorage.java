package com.colorit.backend.storages.storageimpls;

import com.colorit.backend.storages.IStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;

@Component
@ConditionalOnExpression("'${heroku_var}' != 'true'")
public class LocalStorage implements IStorage {
    private static final String STATIC_CONTENT_PATH = System.getProperty("user.home") + "/static/";
    private MessageDigest md5;

    public LocalStorage() throws NoSuchAlgorithmException {
        md5 = MessageDigest.getInstance("md5");
    }

    @Override
    public String writeFile(File file) throws Exception {
        final String extension = FilenameUtils.getExtension(file.getName());
        final byte[] nameHash = md5.digest(file.getName().getBytes());
        final BigInteger bigInt = new BigInteger(1, nameHash);
        final StringBuilder newNameBuilder = new StringBuilder(bigInt.toString(16));
        newNameBuilder.insert(2, '/');
        newNameBuilder.insert(5, '/');
        newNameBuilder.append('.');
        newNameBuilder.append(extension);

        final File dirs = new File(STATIC_CONTENT_PATH + newNameBuilder.substring(0, 5));
        if (!dirs.mkdirs()) {
            throw new Exception("cant create dirs");
        }

        final String newName = newNameBuilder.toString();
        if (!file.renameTo(new File(STATIC_CONTENT_PATH + newName))) {
            throw new Exception("cant rename file");
        }

        return newName;
    }

    @Override
    public byte[] readFile(String path) throws IOException {
        final Path pathToFile = Paths.get(STATIC_CONTENT_PATH + path);
        return Files.readAllBytes(pathToFile);
    }
}
