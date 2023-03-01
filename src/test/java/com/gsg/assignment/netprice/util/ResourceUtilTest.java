package com.gsg.assignment.netprice.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootTest
class ResourceUtilTest {

  @Value("classpath:vat.json")
  private Resource validResource;
  private static Resource invalidResource;

  @BeforeAll
  public static void setUp() {
    invalidResource = new ClassPathResource("invalid_vat.json");
  }

  @Test
  public void testTransformValidJsonToMap() {
    Map<String, BigDecimal> expected = new HashMap<>();
    expected.put("DE", new BigDecimal("0.19"));
    expected.put("FR", new BigDecimal("0.20"));
    Map<String, BigDecimal> actual = ResourceUtil.transformTaxRateJsonToMap(validResource);

    assertEquals(expected.get("DE"), actual.get("DE"));
    assertEquals(expected.get("FR"), actual.get("FR"));
  }

  @Test
  public void testTransformInvalidJsonToMap() {
    assertThrows(RuntimeException.class, () -> ResourceUtil.transformTaxRateJsonToMap(invalidResource));
  }

  @Test
  public void testTransformNonExistentFileToMap() {
    Resource resource = new ClassPathResource("nonexistent.json");
    assertThrows(RuntimeException.class, () -> ResourceUtil.transformTaxRateJsonToMap(resource));
  }
}
