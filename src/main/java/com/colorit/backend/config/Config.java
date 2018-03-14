package com.colorit.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class Config
{
    @Autowired
    Environment mEnv;

    @Bean(name = "front_url_local")
    public String getFrontUrlLocal() {
        return mEnv.getProperty("front_url_local");
    }

    @Bean(name = "front_url_local")
    public String getFrontUrlHeroku() {
        return mEnv.getProperty("front_url_heroku");
    }

    @Bean(name="cloud_name")
    public String getCloudinaryCloudName()
    {
        return mEnv.getProperty("cloud_name");
    }

    @Bean(name="api_key")
    public String getCloudinaryApiKey()
    {
        return mEnv.getProperty("api_key");
    }

    @Bean(name="api_secret")
    public String getCloudinaryApiSecret()
    {
        return mEnv.getProperty("api_secret");
    }
};
