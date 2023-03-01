package com.gsg.assignment.netprice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

public class ResourceUtil {

  private ResourceUtil() {
  }

  public static Map<String, BigDecimal> transformTaxRateJsonToMap(Resource resource) {
    try {
      String jsonString = Files.readString(resource.getFile().toPath());
      ObjectMapper map = new ObjectMapper();
      Map<String, String> jsonMapper = map.readValue(jsonString, new TypeReference<Map>() {
      });
      return jsonMapper.entrySet().stream()
          .collect(Collectors.toMap(t -> t.getKey(), e -> new BigDecimal(e.getValue())));
    } catch (IOException e) {
      throw new RuntimeException("Unable to read vat file", e);
    }
  }
}
