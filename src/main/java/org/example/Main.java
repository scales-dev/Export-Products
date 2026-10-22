package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
  private static final String SITE = "https://myexoticfruit.com";

  private static final String API = "https://webshop.one.com/api/v2/cluster/myexoticfruit.com/domain/myexoticfruit.com/product";
  private static final int ITEMS_PER_REQUEST = 70;

  private static final HttpClient CLIENT = HttpClient.newHttpClient();

  private static final HashMap<String, String> ITEM_DEFAULT_PARAMS = new HashMap<>(Map.of(
          "sortBy", "MANUAL",
          "sort", "ASC",
          "pageOffset", "0",
          "pageSize", String.valueOf(ITEMS_PER_REQUEST),
          "filters", "{}"
  ));

  static void main() {
    int totalItems = requestOneCom(API).getAsJsonObject("data").get("totalCount").getAsInt();

    for(int i = 0; i < totalItems;) {
      ITEM_DEFAULT_PARAMS.put("pageOffset", String.valueOf(i));
      String REQUEST_URL = API + encodeParams(ITEM_DEFAULT_PARAMS);

      System.out.println(REQUEST_URL);
      JsonArray products = requestOneCom(REQUEST_URL)
              .getAsJsonObject("data")
              .getAsJsonArray("result");

      for (JsonElement product : products) {
        System.out.println(product.getAsJsonObject().get("name").getAsString() + " : " + i);
        i++;
      }
    }
  }

  private static JsonObject requestOneCom(String URL) {
    try{
      String response = CLIENT
              .send(HttpRequest.newBuilder()
                      .uri(URI.create(URL))
                      .header("x-one-ws-client-type", "WebshopShopfront")
                      .header("x-one-ws-preview", "false")
                      .header("Content-Type", "application/json")
                      .header("Referer", SITE+"/")
                      .header("Origin", SITE)
                      .header("Accept", "application/json, text/plain, */*")
                      .header("User-Agent", "Mozilla/5.0")
                      .GET()
                      .build(), HttpResponse.BodyHandlers.ofString())
              .body();
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