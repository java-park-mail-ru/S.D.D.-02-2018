package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

@Component
@ConditionalOnExpression("'${heroku_var}' == 'true'")
public class CloudinaryStorage implements IStorage {
    private final Cloudinary cloudinary;
    private final String apiKey;
    private final String apiSecret;
    private final String cloudName;

    public CloudinaryStorage(@Value("${cloud_name}") String cloudName,
                             @Value("${api_key}") String apiKey,
                             @Value("${api_secret}") String apiSecret) {
        this.cloudName = cloudName;
        this.apiSecret = apiSecret;
        this.apiKey = apiKey;
        this.cloudinary = new Cloudinary("cloudinary://" + apiKey + ':' + apiSecret + '@' + cloudName);

    }

    @Override
    public String writeFile(File file) throws IOException, JSONException {
        final Map cloudResponse = this.cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        final JSONObject json = new JSONObject(cloudResponse);
        return json.getString("url");
    }

    @Override
    public byte[] readFile(String path) throws IOException {
        try (InputStream in = new URL(path).openStream()) {
            return in.readAllBytes();
        }
    }
}
