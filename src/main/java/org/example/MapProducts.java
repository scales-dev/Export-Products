package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.stream.Collectors;

public class MapProducts {
  private static final String RELATED_PRODUCTS_API_BASE =
          "https://webshop.one.com/api/v2/cluster/myexoticfruit.com/domain/myexoticfruit.com/product/%s/related-products";

  // not exactly my finest code, but i dont think theres any better way to do any of this
  public static ArrayList<Map<String, String>> getProductMaps(Map<String, JsonObject> products) {
    ArrayList<Map<String, String>> productMaps = new ArrayList<>();

    for (JsonObject product : products.values()) {
      List<JsonElement> categories = product.get("categories").getAsJsonArray().asList();
      String mainCategory = getCategoryPath(categories.isEmpty() ? null : categories.getFirst());
      String extraCategories =  categories.stream()
              .skip(1)
              .map(MapProducts::getCategoryPath)
              .collect(Collectors.joining(":"));

      List<JsonElement> images = product.get("images").getAsJsonArray().asList();

      List<JsonObject> variants = product.get("variants").getAsJsonArray().asList()
              .stream().map(JsonElement::getAsJsonObject).collect(Collectors.toList());
      JsonObject cheapestVariant = variants.stream()
              .min(Comparator.comparingDouble(variant -> variant.get("price").getAsDouble()))
              // even products with no variants set are sent with a single variant, W EKM
              .orElseThrow();

      List<JsonObject> additionalInfo = product.get("additionalInfo").getAsJsonArray().asList()
              .stream().map(JsonElement::getAsJsonObject).toList();

      Map<String, String> productMap = new LinkedHashMap<>();
      productMap.put("Action", "Add Product");

      String name = product.get("name").getAsString();
      System.out.println(name + " is being scraped!");

      productMap.put("Name", name);
      productMap.put("Description", product.get("description").getAsString());

      productMap.put("CategoryPath", mainCategory);
      productMap.put("CategoryManagement", extraCategories);

      productMap.put("Image1", getImage(images, 0));
      productMap.put("Image2", getImage(images, 1));
      productMap.put("Image3", getImage(images, 2));
      productMap.put("Image4", getImage(images, 3));
      productMap.put("Image5", getImage(images, 4));

      JsonElement pageTitle = product.get("pageTitle");
      productMap.put("MetaTitle", pageTitle.isJsonNull() ? name : pageTitle.getAsString());
      productMap.put("MetaDescription", product.get("meta").getAsString());
      String keywords = product.get("keywords").getAsJsonArray().asList()
              .stream()
              .map(JsonElement::getAsString)
              .collect(Collectors.joining(","));
      productMap.put("MetaKeywords", keywords);

      productMap.put("Price", cheapestVariant.getAsJsonObject().get("price").getAsString());

      JsonObject relatedProductsResponse = NetworkUtil.requestURL(
              String.format(RELATED_PRODUCTS_API_BASE, product.get("id").getAsString()),
              Map.of("autoSuggest", "true"),
              Main.ONE_COM_HEADERS
      );
      List<JsonElement> relatedProductIds =
              relatedProductsResponse.get("data").getAsJsonObject().get("result").getAsJsonArray().asList();

      String relatedProducts = relatedProductIds.stream()
              .map(idObject -> {
                String id = idObject.getAsJsonObject().get("relatedProductId").getAsString();
                JsonObject relatedProductObject = products.get(id);

                return relatedProductObject == null
                        ? null
                        : relatedProductObject.get("name").getAsString();
              })
              // one.com goes rogue and suggests id's which arent real
              .filter(Objects::nonNull)
              .collect(Collectors.joining(","));

      productMap.put("Related Products", relatedProducts);

      String aka = getTab("Also Known As", additionalInfo);
      productMap.put("Tab Content:Also Known As", aka);

      String origin = getTab("Origin", additionalInfo);
      productMap.put("Tab Content:Origin", origin);

      String preparation = getTab("Preparation", additionalInfo);
      productMap.put("Tab Content:Preparation", preparation);

      String usage = getTab("Usage", additionalInfo);
      productMap.put("Tab Content:Usage", usage);

      String storage = getTab("Storage", additionalInfo);
      productMap.put("Tab Content:Storage", storage);

      String averageWeight = getTab("Average Weight", additionalInfo);
      productMap.put("Tab Content:Average Weight", averageWeight);

      String furtherInfo = getTab("Further Info", additionalInfo);
      productMap.put("Tab Content:Further Info", furtherInfo);

      productMaps.add(productMap);

      if (variants.size() == 1) continue;

      addProductVariants(product, mainCategory, variants, cheapestVariant, productMaps);
    }

    return productMaps;
  }

  private static String getCategoryPath(JsonElement category) {
    return category == null
            ? "Home > Shop Online"
            : "Home > Shop Online > " + category.getAsJsonObject().get("name").getAsString();
  }

  private static String getImage(List<JsonElement> images, int i) {
    return i >= images.size()
            ? null
            : images.get(i).getAsJsonObject().get("url").getAsString();
  }

  private static String getTab(String name, List<JsonObject> additionalInfo) {
    JsonObject tabObject = additionalInfo.stream()
            .filter(tab -> tab.get("title").getAsString().equalsIgnoreCase(name))
            .findFirst().orElse(null);
    if (tabObject == null) return null;

    String description = tabObject.get("description").getAsString();
    return isHtmlEmpty(description) ? null : description;
  }

  private static boolean isHtmlEmpty(String html) {
    // remove tags, cause they dont contain visible text (ig they can have images, but they dont for this case)
    html = html.replaceAll("<[^>]*>", "");
    html = html.trim();

    // in case you (like me) hadnt heard of (?i) before https://stackoverflow.com/a/7660977
    // replace n/a if the string starts with it
    html = html.replaceAll("^(?i)n/a", "");
    // ditto for tbc
    html = html.replaceAll("^(?i)tbc", "");

    // clear leading punctuation
    html = html.replaceAll("^[.\\-_]*", "");

    return html.trim().isEmpty();
  }


  private static void addProductVariants(
          JsonObject product,
          String category,
          List<JsonObject> variants,
          JsonObject cheapestVariant,
          List<Map<String, String>> productMaps
  ) {
    for (JsonObject variant : variants) {
      Map<String, String> variantMap = new LinkedHashMap<>();

      variantMap.put("Action", "Add Product Variant");

      variantMap.put("Name", product.get("name").getAsString());
      variantMap.put("CategoryPath", category);

      variantMap.put("Price", variant.get("price").getAsString());

      variantMap.put("VariantNames", "Choose your quantity");
      variantMap.put("VariantTypes", "DROPDOWN");

      JsonObject options = variant.get("optionsValues").getAsJsonObject();
      // variant items are stored as  "optionsValues": {"Choose your quantity;": "2kg"}
      variantMap.put("VariantItem1", options.get(options.keySet().stream().findFirst().orElseThrow()).getAsString());

      // they fr use "Yes" and "No" :sob: https://tags.ekm.com/ states:
      // "A true or false answer either shown as true|false or yes|no or 1|0. EKM typically uses yes or no."
      variantMap.put("VariantDefault", variant.equals(cheapestVariant) ? "Yes" : "No");

      productMaps.add(variantMap);
    }
  }
}
