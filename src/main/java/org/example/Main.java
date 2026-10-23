package org.example;

import com.google.gson.JsonObject;

import java.nio.file.Path;
import java.util.*;

public class Main {
  public static final Map<String, String> ONE_COM_HEADERS = Map.of(
          "x-one-ws-client-type", "WebshopShopfront",
          "x-one-ws-preview", "false",
          "Content-Type", "application/json",
          "Referer", "https://myexoticfruit.com/",
          "Origin", "https://myexoticfruit.com",
          "Accept", "application/json, text/plain, */*",
          "User-Agent", "Mozilla/5.0"
  );

  static void main() {
    Map<String, JsonObject> products = RequestItems.getProducts();
    ArrayList<Map<String, String>> ekmProductMaps = MapProducts.getProductMaps(products);

    Path outputPath = Path.of("./products.csv");
    CsvWriter.writeCsv(outputPath, ekmProductMaps);
    System.out.println("Successfully wrote to " + outputPath);
  }
}