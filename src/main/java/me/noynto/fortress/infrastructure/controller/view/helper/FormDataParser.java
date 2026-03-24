package me.noynto.fortress.infrastructure.controller.view.helper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FormDataParser {

    public static Map<String, String> execute(String body) {
        Map<String, String> data = new HashMap<>();
        if (body == null || body.isEmpty()) {
            return data;
        }
        for (String pair : body.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                data.put(
                        java.net.URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                        java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8)
                );
            }
        }
        return data;
    }

}
