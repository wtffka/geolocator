package com.mygeolocator.utils;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.mygeolocator.utils.AppConstants.GEOLOCATION_SERVICE_URL;
import static com.mygeolocator.utils.AppConstants.SPECIFY_THE_DATA;

@Component
public class ConnectionToGeolocationService {

    public static Map<String, String> requestApi(String requestAddress) throws IOException {

        String fullUrl = GEOLOCATION_SERVICE_URL
                + URLEncoder.encode(requestAddress, java.nio.charset.StandardCharsets.UTF_8.toString());

        JSONObject json = JSONReader.readJsonFromUrl(fullUrl);

        Map<String, String> locationMap;

        try {
            if (requestAddress.matches("[^a-zA-Zа-яА-Я]+")) {
                locationMap = parseLocationToMap(getLocation(json));
            } else {
                if (isUniqueResult(json)) {
                    locationMap = parseLocationToMap(getLocation(json));
                    locationMap.put("coordinates", getCoordinates(json));
                } else {
                    throw new RuntimeException(SPECIFY_THE_DATA);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(SPECIFY_THE_DATA);
        }

        return locationMap;
    }

    private static boolean isUniqueResult(JSONObject json) {
        return json.toString().split("\"found\":\"")[1].split("\"")[0].equals("1");
    }

    private static String getLocation(JSONObject json) {
        String location = json.toString().split("\"Components\":\\[")[1].split(",\"AddressDetails")[0];
        return location.replace("{", "");
    }

    private static String getCoordinates(JSONObject json) {
        return json.toString().split("\"pos\":\"")[1].split("\"")[0];
    }

    private static Map<String, String> parseLocationToMap(String location) {
        Map<String, String> locationMap = new LinkedHashMap<>();
        String[] locationParts = location.split("}");
        for (String locationPart : locationParts) {
            locationPart = locationPart.replace("\"", "");
            if (locationPart.contains("kind")) {
                String key = locationPart.split("kind:")[1].split(",")[0];
                String value = locationPart.split("name:")[1].split("}")[0];
                locationMap.put(key, value);
            }
        }
        return locationMap;
    }
}
