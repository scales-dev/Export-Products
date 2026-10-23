package org.example;

import com.google.gson.JsonObject;

import java.nio.file.Path;
import java.util.*;

public class Main {
  static void main() {
    ArrayList<JsonObject> products = RequestItems.getProducts();
    ArrayList<Map<String, String>> ekmProductMaps = MapProducts.getProductMaps(products);

    Path outputPath = Path.of("./products.csv");
    CsvWriter.writeCsv(outputPath, ekmProductMaps);
    System.out.println("Successfully wrote to " + outputPath);
  }

  /*
  // do later
  ProductSummary

  RelatedProducts

  Attribute:AFRICA (h)
  Attribute:ASIA (h)
  Attribute:AUSTRALIA (h)
  Attribute:EUROPE (h)
  Attribute:NORTHAMERICA (h)
  Attribute:SOUTHAMERICA (h)

  Attribute:APR (h)
  Attribute:AUG (h)
  Attribute:DEC (h)
  Attribute:FEB (h)
  Attribute:JAN (h)
  Attribute:JUL (h)
  Attribute:JUN (h)
  Attribute:MAR (h)
  Attribute:MAY (h)
  Attribute:NOV (h)
  Attribute:OCT (h)
  Attribute:SEP (h)
   */
}