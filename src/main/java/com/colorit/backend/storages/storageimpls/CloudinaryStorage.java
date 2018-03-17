package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import org.cloudinary.json.JSONObject;

import java.io.File;
import java.util.Map;

public class CloudinaryStorage implements IStorage {
    private Cloudinary cloudinary;
    private static String apiKey = System.getenv().get("API_KEY");
    private static String apiSecret = System.getenv().get("API_SECRET");
    private static String cloudName = System.getenv().get("CLOUD_NAME");

    public CloudinaryStorage() {
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
