package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

public class CsvWriter {
  public static void writeCsv(Path file, ArrayList<Map<String, String>> csv) {
    LinkedHashSet<String> headers = new LinkedHashSet<>();
    csv.forEach(product -> headers.addAll(product.keySet()));

    try{
      BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
      CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

      csvPrinter.printRecord(headers);

      for (Map<String, String> item : csv) {
        csvPrinter.printRecord(headers.stream().map(item::get).toList());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}