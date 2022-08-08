package com.mygeolocator.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.mygeolocator.utils.AppConstants.SEARCH_SERVICE_URL;
import static com.mygeolocator.utils.AppConstants.WIKI_START_PAGE;

@Component
public class ConnectionToSearchService {
    public static String requestApi(String request) throws IOException, InterruptedException {
        String fullUrl = SEARCH_SERVICE_URL
                + URLEncoder.encode(request, java.nio.charset.StandardCharsets.UTF_8.toString());

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .build();

        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String response = httpResponse.body().toString();

        return WIKI_START_PAGE + parseResponseBodyToSearchResult(response);
    }

    private static String parseResponseBodyToSearchResult(String responseBody) {
        return responseBody.split("tabindex=\"0\" href=\"https://ru.wikipedia.org/wiki/")[1].split("\"")[0];
    }

}
