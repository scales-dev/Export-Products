package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestItems {
  private static final String API = "https://webshop.one.com/api/v2/cluster/myexoticfruit.com/domain/myexoticfruit.com/product";
  private static final int ITEMS_PER_REQUEST = 70;

  private static final Map<String, String> ITEM_DEFAULT_PARAMS = Map.of(
          "sortBy", "MANUAL",
          "sort", "ASC",
          "pageOffset", "0",
          "pageSize", String.valueOf(ITEMS_PER_REQUEST),
          "filters", "{}"
  );


  public static Map<String, JsonObject> getProducts() {
    HashMap<String, JsonObject> items = new LinkedHashMap<>();

    int totalItems = NetworkUtil.requestURL(API, Map.of(), Main.ONE_COM_HEADERS)
            .getAsJsonObject("data")
            .get("totalCount").getAsInt();

    HashMap<String, String> params = new HashMap<>(ITEM_DEFAULT_PARAMS);
    for(int i = 0; i < totalItems;) {
      params.put("pageOffset", String.valueOf(i));

      for (JsonElement product : NetworkUtil.requestURL(API, params, Main.ONE_COM_HEADERS)
              .getAsJsonObject("data")
              .getAsJsonArray("result")) {
        i++;
        JsonObject productObject = product.getAsJsonObject();
        items.put(productObject.get("id").getAsString(), productObject);
      }
    }

    return items;
  }
}