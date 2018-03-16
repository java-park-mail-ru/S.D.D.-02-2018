package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;

public class CloudinaryStorage implements IStorage {
    private Cloudinary cloudinary;
    private final String apiKey;
    private final String apiSecret;
    private final String cloudName;

    public CloudinaryStorage(String apiKey, String apiSecret, String cloudName) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.cloudName = cloudName;
        this.cloudinary = new Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
    }

    @Override
    public String writeFile(File file) throws Exception {
        Map cloudResponse = this.cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        JSONObject json = new JSONObject(cloudResponse);
        return json.getString("url");
    }

    @Override
    public byte[] readFile(String file) {
        return null;
    }
}
