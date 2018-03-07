package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import com.colorit.backend.storages.responses.AbstractStorageResponse;
import org.cloudinary.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Map;

public class CloudinaryStorage implements IStorage {
    private Cloudinary cloudinary;
    public CloudinaryStorage() {
        super();
        this.cloudinary = new Cloudinary("http://c");
    }

    @Override
    public AbstractStorageResponse writeFile(File file)
    {

        try {
            Map response = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            JSONObject json = new JSONObject(response);
            String url = json.getString("url");
        } catch (IOException iOEx) {

        }
        return null;
    }

    @Override
    public AbstractStorageResponse readFile(String file) {
        return null;
    }
}
