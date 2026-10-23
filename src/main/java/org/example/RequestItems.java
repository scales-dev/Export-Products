package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestItems {
  private static final String SITE = "https://myexoticfruit.com";

  private static final String API = "https://webshop.one.com/api/v2/cluster/myexoticfruit.com/domain/myexoticfruit.com/product";
  private static final int ITEMS_PER_REQUEST = 70;

  private static final Map<String, String> ITEM_DEFAULT_PARAMS = Map.of(
          "sortBy", "MANUAL",
          "sort", "ASC",
          "pageOffset", "0",
          "pageSize", String.valueOf(ITEMS_PER_REQUEST),
          "filters", "{}"
  );

  private static final Map<String, String> ONE_COM_HEADERS = Map.of(
          "x-one-ws-client-type", "WebshopShopfront",
          "x-one-ws-preview", "false",
          "Content-Type", "application/json",
          "Referer", SITE+"/",
          "Origin", SITE,
          "Accept", "application/json, text/plain, */*",
          "User-Agent", "Mozilla/5.0"
  );


  public static ArrayList<JsonObject> getProducts() {
    ArrayList<JsonObject> items = new ArrayList<>();

    int totalItems = NetworkUtil.requestURL(API, Map.of(), ONE_COM_HEADERS)
            .getAsJsonObject("data")
            .get("totalCount").getAsInt();

    HashMap<String, String> params = new HashMap<>(ITEM_DEFAULT_PARAMS);
    for(int i = 0; i < totalItems;) {
      params.put("pageOffset", String.valueOf(i));

      for (JsonElement product : NetworkUtil.requestURL(API, params, ONE_COM_HEADERS)
              .getAsJsonObject("data")
              .getAsJsonArray("result")) {
        i++;
        items.add(product.getAsJsonObject());
      }
    }

    return items;
  }
}