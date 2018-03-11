package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class CloudinaryStorage implements IStorage {
    private Cloudinary cloudinary;
    private String apiKey;
    private String apiSecret;
    private String cloudName;

    public CloudinaryStorage() {
        // todo autowire value
        cloudName = System.getenv().get("CLOUD_NAME");
        apiKey = System.getenv().get("API_KEY");
        apiSecret = System.getenv().get("API_SECRET");
        this.cloudinary = new Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
    }

    @Override
    public String writeFile(File file) throws Exception {
        Map cloudResponse = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        JSONObject json = new JSONObject(cloudResponse);
        return json.getString("url");
    }

    @Override
    public byte[] readFile(String file) {
        return null;
    }
}
