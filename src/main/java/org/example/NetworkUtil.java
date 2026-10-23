package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworkUtil {
  private static final HttpClient CLIENT = HttpClient.newHttpClient();

  public static JsonObject requestURL(String URL, Map<String, String> paramsMap, Map<String, String> headersMap) {
    URL += encodeParams(paramsMap);

    HttpRequest.Builder request = HttpRequest.newBuilder().uri(URI.create(URL));
    for (Map.Entry<String, String> entry : headersMap.entrySet()) {
      request.header(entry.getKey(), entry.getValue());
    }

    try{
      String response = CLIENT.send(request.GET().build(), HttpResponse.BodyHandlers.ofString()).body();
      return JsonParser.parseString(response).getAsJsonObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String encodeParams(Map<String, String> paramsMap) {
    return "?" + paramsMap.entrySet().stream()
            .map(entry -> entry.getKey() + "="
                    + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));
  }
}