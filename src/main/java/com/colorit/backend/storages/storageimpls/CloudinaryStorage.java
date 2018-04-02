package com.colorit.backend.storages.storageimpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.colorit.backend.storages.IStorage;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.File;
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
                      @Value("${api_secret}") String apiSeret) {
        this.cloudName = cloudName;
        this.apiSecret = apiSeret;
        this.apiKey = apiKey;
        this.cloudinary = new Cloudinary("cloudinary://" + apiKey + ':' + apiSecret + '@' + cloudName);

    }

    @Override
    public String writeFile(File file) throws Exception {
        final Map cloudResponse = this.cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        final JSONObject json = new JSONObject(cloudResponse);
        return json.getString("url");
    }

    @Override
    public byte[] readFile(String file) {
        return null;
    }
}
