package tests.api.common;


import net.minidev.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;

public class TestRequestBuilder {
    private final ArrayList<String> jsonKeys;

    public TestRequestBuilder() {
        jsonKeys = new ArrayList<>();
    }

    public void init(String... keys) {
        Collections.addAll(jsonKeys, keys);
    }

    public static String getJsonRequestForSignUp(@NotNull String uName, @NotNull String uEmail,
                                          @NotNull String uPassword, @NotNull String uPasswordCheck) {
        final JSONObject jso = new JSONObject();
        jso.put("nickname", uName);
        jso.put("password", uPassword);
        jso.put("passwordCheck", uPasswordCheck);
        jso.put("email", uEmail);
        return jso.toString();
    }

    public String getJsonRequest(String... values) {
        final JSONObject jso = new JSONObject();

        if (values == null) {
            jso.put(jsonKeys.get(0), values);
        } else {
            for (int i = 0; i < jsonKeys.size(); i++) {
                jso.put(jsonKeys.get(i), values[i]);
            }
        }
        return jso.toString();
    }
}